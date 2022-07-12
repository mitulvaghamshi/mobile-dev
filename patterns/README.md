# Patterns - Flutter ffi

A new Flutter plugin project.

## Getting Started

This project is a starting point for a Flutter
[plug-in package](https://flutter.dev/developing-packages/),
a specialized package that includes platform-specific implementation code for
Android and/or iOS.

For help getting started with Flutter development, view the
[online documentation](https://flutter.dev/docs), which offers tutorials,
samples, guidance on mobile development, and a full API reference.

# Android/build.gradle
```gradle
externalNativeBuild {
    cmake {
        path "../libs/CMakeLists.txt"
    }
}
```

# iOS/Classes

```
ln -s ../libs/native_test.cpp
```

# macOS/Classes

```
ln -s ../libs/native_test.cpp
```

# Windows

```cmake
# Modify CMakeLists.txt
add_library(native_test SHARED ../libs/native_test.cpp ../libs/exports.def)
```

# Linux

```cmake
ln -s ../libs ./libs
# Modify CMakeLists.txt
add_subdirectory("./libs")
# add this line at the very end of the install commands
install(FILES ${PROJECT_BINARY_DIR}/libs/libnative_test.so DESTINATION "${INSTALL_BUNDLE_LIB_DIR}" COMPONENT Runtime)
```
