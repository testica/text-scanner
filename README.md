# Text Scanner (OCR) [![GitHub release](https://img.shields.io/github/release/testica/text-scanner.svg)]()
OCR Android App using tesseract

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
- [admob](https://developers.google.com/admob/android/quick-start): to show google ads (to pay some bills). Check [PR #8](https://github.com/testica/text-scanner/pull/8) to know more about admob environment.

## Support
- Android 4.0 +

## Planned Features
On [Projects tab](https://github.com/testica/text-scanner/projects/1) you can trace the future possible features and its status.
