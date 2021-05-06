# MedDataShare

This repository is a part of master thesis at the [Faculty of Technical Sciences](http://www.ftn.uns.ac.rs/n1386094394/faculty-of-technical-sciences).

Purpose of this work is to provide a platform for medical data sharing in healthcare industry using blockchain technologies. Patients, medical staff, medical institution's admin and system admin are possible roles in the network. It is patient-centric system which means that patient is a owner of all his examinations. He has full rights to arrange who can access to his documents and under which conditions.

Basic functionalities of the application are:
* User registration
* Displaying and editing profile information
* Searching medical documents and requesting access to the particular one
* Submiting clinical examination via medical staff
* Viewing patient's examination
* Defining access rights for patient's own health documents by himself
* Making decision upon examination access request  

## System architecture
![system_architecture](https://user-images.githubusercontent.com/44602021/117282128-b1548100-ae64-11eb-9958-4bf3958a60a5.png)


## Configuration setup
Hyperledger Fabric is used for blockchain network configuration. For installing Fabric and all necessary programs visit [prerequisites](https://hyperledger-fabric.readthedocs.io/en/latest/prereqs.html) and [installation process](https://hyperledger-fabric.readthedocs.io/en/latest/install.html). Latest version
of Hyperledger Fabric is implemented in this project. The configuration and organization of the network is similar to Fabric [test network](https://hyperledger-fabric.readthedocs.io/en/latest/test_network.html).

In addition, you need to install Java, MySql and MongoDB in order to properly run backend applications in the project. Afterwards, you have to adapt database connection parameters at your values.

Script [**start.sh**](https://github.com/lukajvnv/medDataShare/blob/main/start.sh) is a shortland for starting all applications and if every module is corectly setup, MedDataShare platform will be launched.
