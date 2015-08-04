# simple-milight

Java API for Milight (LimitlessLED) light bulbs.

Current implementation only supports instruction set for RGB+W bulbs.

To use Simple Milight in Java, include the package cz.semecky.simplemilight in your project. There are no other library dependencies.

## Usage
__Turn on all lights:__
```java
String address = "192.168.0.105";  // ... for example.
RGBW.all.on().send(address);
```

__Set lights in zone 1 to white:__

```java
String address = "192.168.0.105";  // ... for example.
RGBW.zone(1).white().send(address);
```
