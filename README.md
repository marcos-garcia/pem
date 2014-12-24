# Process Event Manager
 
This product manage events for command line executions, which activates when an associate regular expression matches with a line that appears in the stdout stream.
 
## Installation
 
To install it, just add this dependency to your pom.xml file in your project:

```
<dependency>
    <groupId>com.marcosgarciacasado</groupId>
    <artifactId>pem</artifactId>
    <version>1.0</version>
</dependency>
```
 
## Usage
 
Provides the classes necessary to launch a process and activate observer events when the stdout of the process launched matches a specific regular expression.
The package involves three kind of entities:

An observable process in charge of the management of process execution.
A process status marks the status of a process.
An observable process event which is activated by the process associated when its stdout
matches it's regular expression. Then, the event notifies it's observers about being activated. It observes the process, and it is observed by external objects.

An example of usage is located in the test suite, at https://github.com/marcos-garcia/pem/blob/master/src/test/java/com/marcosgarciacasado/pem/ObservableProcessTest.java
 
## Contributing
 
1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D
 
## History
 
1.0: First release of the package
 
## Credits
 
Developed by: 	Marcos García Casado
Personal web:	http://marcosgarciacasado.com
Email:			marcosgarciacasado@gmail.com
 
## License
 
GNU GENERAL PUBLIC LICENSE, Version 2, June 1991
http://www.gnu.org/licenses/gpl-2.0.txt
