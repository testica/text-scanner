# Text Scanner (OCR) [![GitHub release](https://img.shields.io/github/release/testica/text-scanner.svg)]()
OCR Android App using tesseract

<a href='https://play.google.com/store/apps/details?id=com.ltapps.textscanner&hl=en&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/badge_new.png'/></a>

## Features
- Crop
- Rotate
- Binarize
- Recognize Text (English and spanish)
  - Search
  - Copy to clipboard
  
## Libraries
- [android-image-cropper](https://github.com/ArthurHub/Android-Image-Cropper): for image cropping.
- [tess-two](https://github.com/rmtheis/tess-two): to recognize text (tesseract) and binarize (leptonica).
- [firebase](https://firebase.google.com/docs/android/setup): to report metrics (for development purpose, you can [remove firebase dependencies](https://github.com/testica/text-scanner/pull/5) or add your own google-services.json).
- [admob](https://developers.google.com/admob/android/quick-start): to show google ads (to can pay some bills). Check [PR #8](https://github.com/testica/text-scanner/pull/8) to know more about admob environment.

## Support
- Android 4.0 +

## Planned Features
On [Projects tab](https://github.com/testica/text-scanner/projects/1) you can trace the future possible features and its status.

**Backlog**     → a simple idea 

**Ready**       → idea now is a possible feature waiting to be developed

**In progress** → feature being developed
