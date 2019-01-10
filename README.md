# Yildiz-Engine module-vfs-physfs.

This is the official repository of The Physfs VFS Module, part of the Yildiz-Engine project.
The physfs module is an implementation of the module-vfs, it is based on the physfs 3 library.

## Features

* Multiple format supported.
* File read.
* File write.
* ...

## Requirements

To build this module, you will need the latest java JDK, and Maven 3.

## Coding Style and other information

Project website:
http://www.yildiz-games.be

Issue tracker:
https://yildiz.atlassian.net

Wiki:
https://yildiz.atlassian.net/wiki

Quality report:
https://sonarqube.com/overview?id=be.yildiz-games:module-vfs-physfs

## License

All source code files are licensed under the permissive MIT license
(http://opensource.org/licenses/MIT) unless marked differently in a particular folder/file.

## Build instructions

Go to your root directory, where you POM file is located.

	mvn clean install

This will compile the source code, then run the unit tests, and finally build a jar file.

## Usage

In your maven project, add the dependency

```xml
<dependency>
    <groupId>be.yildiz-games</groupId>
    <artifactId>module-vfs-physfs</artifactId>
    <version>${currentVersion}</version>
</dependency>
```

## Contact
Owner of this repository: Grégory Van den Borre