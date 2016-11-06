package com.ltapps.textscanner;

public class OtsuThresholder
{
	private int histData[];
	private int maxLevelValue;
	private int threshold;

	public OtsuThresholder()
	{
		histData = new int[256];
	}

	public int[] getHistData()
	{
		return histData;
	}

	public int getMaxLevelValue()
	{
		return maxLevelValue;
	}

	public int getThreshold()
	{
		return threshold;
	}

	public int doThreshold(byte[] srcData)
	{
		int ptr;

		// Clear histogram data
		// Set all values to zero
		ptr = 0;
		while (ptr < histData.length) histData[ptr++] = 0;

		// Calculate histogram and find the level with the max value
		// Note: the max level value isn't required by the Otsu method
		ptr = 0;
		maxLevelValue = 0;
		while (ptr < srcData.length)
		{
			int h = 0xFF & srcData[ptr];
			histData[h] ++;
			if (histData[h] > maxLevelValue) maxLevelValue = histData[h];
			ptr ++;
		}

		// Total number of pixels
		int total = srcData.length;

		float sum = 0;
		for (int t=0 ; t<256 ; t++) sum += t * histData[t];

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		threshold = 0;

		for (int t=0 ; t<256 ; t++)
		{
			wB += histData[t];					// Weight Background
			if (wB == 0) continue;

			wF = total - wB;						// Weight Foreground
			if (wF == 0) break;

			sumB += (float) (t * histData[t]);

			float mB = sumB / wB;				// Mean Background
			float mF = (sum - sumB) / wF;		// Mean Foreground

			// Calculate Between Class Variance
			float varBetween = (float)wB * (float)wF * (mB - mF) * (mB - mF);	

			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
		}


		return threshold;
	}
}
