#!/bin/bash

# Build script for drone.io
# Usage:
#    source drone.sh
#
# Requires following environment variables:
#    API_KEY
#    API_SECRET

# echo commands being executed but do not show contents of environment variables
set -v

PATH=$(echo $PATH | sed 's/\/opt\/android-sdk-linux//')
sudo apt-get update -qq
sudo apt-get install -qq --force-yes expect
if [ `uname -m` = x86_64 ]; then sudo apt-get install -qq --force-yes libgd2-xpm ia32-libs ia32-libs-multiarch > /dev/null; fi

# Install Android SDK components, including emulator for tests
COMPONENTS="build-tools-19.1.0,android-19,android-16,sys-img-armeabi-v7a-android-16,extra-android-m2repository"
LICENSES="android-sdk-license-5be876d5|android-sdk-preview-license-52d11cd2"
curl -3L https://raw.github.com/embarkmobile/android-sdk-installer/version-2/android-sdk-installer | bash /dev/stdin --install=$COMPONENTS --accept=$LICENSES && source ~/.android-sdk-installer/env

# Setup AVD device
echo no | android create avd --force -n test -t android-16
emulator -avd test -no-skin -no-audio -no-window &

#cp gradle.properties.template gradle.properties
#sed -i 's/Insert your OAuth consumer key here/'"$API_KEY"'/g' gradle.properties
#sed -i 's/Insert your OAuth consumer secret here/'"$API_SECRET"'/g' gradle.properties

# Use the same debug key on every build which
# makes updating from one build to another possible
#cp debug.keystore ~/.android/

# Compile app
./gradlew --daemon assembleDebug

# Run lint
#./gradlew --daemon lintDebug

# Run CheckStyle
#./gradlew --daemon checkstyle

#chmod +x wait_for_emulator
#./wait_for_emulator

# Run tests
#./gradlew --daemon connectedAndroidTest

# Provide test results as an downloadable Artifact
#mv build/outputs/lint-results-debug.html build/outputs/reports/
#mv build/outputs/lint-results-debug_files/ build/outputs/reports/
#cd build/outputs/reports/
#tar czf testResults.tar.gz *

exit 0
