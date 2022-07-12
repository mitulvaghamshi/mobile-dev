Interface and Implementation
In Objective C, the file where the declaration of class is done is called the interface file and the file where the class is defined is called the implementation file.

A simple interface file MyClass.h would look like the following −
```ObjectiveC
@interface MyClass:NSObject { 
   // class variable declared here
}

// class properties declared here
// class methods and instance methods declared here
@end
```

The implementation file MyClass.m would be as follows −
```ObjectiveC
@implementation MyClass
   // class methods defined here
@end
```
