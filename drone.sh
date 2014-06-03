#!/bin/bash

# Build script for drone.io
# Usage:
#    source drone.sh
#
# Requires following environment variables:
#    ANDROID_SDK_TOOLS_VERSION
#    ANDROID_BUILD_TOOLS_VERSION
#    ANDROID_API_LEVEL
#    EMULATOR_API_LEVEL
#    API_KEY
#    API_SECRET

# echo commands being executed but do not show contents of environment variables
set -v

PATH=$(echo $PATH | sed 's/\/opt\/android-sdk-linux//')
sudo apt-get update -qq
if [ `uname -m` = x86_64 ]; then sudo apt-get install -qq --force-yes libgd2-xpm ia32-libs ia32-libs-multiarch > /dev/null; fi
wget https://dl.google.com/android/android-sdk_r22.6.2-linux.tgz -nv
tar xzf android-sdk_r22.6.2-linux.tgz
export ANDROID_HOME=$PWD/android-sdk-linux
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/build-tools

# Install Android SDK components
echo y | android update sdk --filter 
platform-tools,build-tools-$ANDROID_BUILD_TOOLS_VERSION,android-$ANDROID_API_LEVEL,android-$EMULATOR_API_LEVEL,sysimg-$EMULATOR_API_LEVEL,extra-android-m2repository --no-ui --force 
--all > /dev/null

# Setup AVD device
#echo no | android create avd --force -n test -t android-$EMULATOR_API_LEVEL

#emulator -avd test -no-skin -no-audio -no-window &

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
#mv build/lint-results-debug.html build/reports/
#mv build/lint-results-debug_files/ build/reports/
#cd build/reports/
#tar czf testResults.tar.gz *

exit 0
