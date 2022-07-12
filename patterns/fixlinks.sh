# !bin/bash

APP="$HOME/Developer/vscode/patterns"

# iOS
echo "Link native_test.cpp to iOS..."
cd "$APP/ios/Classes"
rm patterns.cpp
rm native_test.cpp
ln -s ../../libs/patterns.cpp
ln -s ../../libs/native_test.cpp

# macOS
echo "Link native_test.cpp to macOS..."
cd "$APP/macOS/Classes"
rm patterns.cpp
rm native_test.cpp
ln -s ../../libs/patterns.cpp
ln -s ../../libs/native_test.cpp

echo "Complete!"
