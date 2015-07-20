# vRealize Resource Deleter

Easily delete VMware vRealize resources.

## Requirements

* JRE 1.7+ 
```
java -version
```
* VMware vRealize 6.0+
* Administrator username and password for your VMware vRealize server
* A box with access to your VMware vRealize server 
```
ping vrealize_server
```

## Installation

1. Download the [jar](https://www.dropbox.com/s/5vcc2cxb5463thp/vrealize-resource-deleter-1.0.1.jar?dl=0)
2. Copy the jar to a box with access to your VMware vRealize server

## Usage

```
java -jar vrealize-resource-deleter-1.0.1.jar [-h vrealize_hostname] [-u vrealize_admin] [-p vrealize_password]
```

There are three ways to specify the vRealize hostname, username, and password.

* Provide them as arguments 
```
java -jar vrealize-resource-deleter-1.0.1.jar [-h vrealize_hostname] [-u vrealize_admin] [-p vrealize_password]
```

or

* Create a .suiteapi file in the current directory (or a parent directory) with this format:
```
host=vrealize_host
user=vrealize_username
password=vrealize_password
```

or

* Simply run the jar and enter the parameters as prompted (recommended if you don't want password to be visible or stored anywhere)
```
java -jar vrealize-resource-deleter-1.0.1.jar
```

## Contributing

1. Fork it!

2. Create your feature branch: 
    ```
    git checkout -b my-new-feature
    ```

3. Commit your changes: 
    ```
    git commit -am 'Add some feature'
    ```

4. Push to the branch: 
    ```
    git push origin my-new-feature
    ```

5. Submit a pull request :D

## History

* April 10, 2015 - Version 0.1.0 - Created project and README
* July 19, 2015 - Version 1.0.0 - Version 1.0 complete; Supports resource and descendent deletion
* July 19, 2015 - Version 1.0.1 - Fix for bug when choosing not to delete descendents

## Credits

Blue Medora LLC

www.bluemedora.com

## License

Apache License

Version 2.0, January 2004

http://www.apache.org/licenses/
