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

1. Download the jar TODO: add link
2. Copy the jar to a box with access to your VMware vRealize server

or

1. Clone this repository 
    ```
    git clone https://github.com/MarkArbogast/vRealize-Resource-Deleter.git
    ```

2. Make the jar 
    ```
    rake build:jar
    ```

3. Copy the jar to a box with access to your VMware vRealize server `scp vrealize-resource-deleter-1.0.0.jar admin@network_box:~`

## Usage

```
java -jar vrealize-resource-deleter-1.0.0.jar [-h vrealize_hostname] [-u vrealize_admin] [-p vrealize_password]
```

There are three ways to specify the vRealize hostname, username, and password.

* Provide them as arguments 
```
java -jar vrealize-resource-deleter-1.0.0.jar [-h vrealize_hostname] [-u vrealize_admin] [-p vrealize_password]
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
java -jar vrealize-resource-deleter-1.0.0.jar
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

## Credits

Blue Medora LLC

www.bluemedora.com

## License

Apache License

Version 2.0, January 2004

http://www.apache.org/licenses/
