#!/bin/bash
REPO_ROOT=$PWD
BACKEND_APP_ROOT=$REPO_ROOT/backend/backend-medDataShare
FHIR_SERVER_ROOT=$REPO_ROOT/backend/fhir_server-medDataShare
FRONTEND_APP_ROOT=$REPO_ROOT/frontend/med-data-share-frontend
TEST_NETWORK=$REPO_ROOT/hyperledger-fabric/test-network

user_group='med_data_share'
user_name='med_data_share_user'

function startAsCustomUser(){
  user_no_exist=$(less /etc/passwd | grep $user_name | wc -l)
  if [ $user_no_exist -eq 0 ]; then
    sudo addgroup $user_group
    sudo adduser --ingroup $user_group --disabled-password $user_name
  fi
  pushd $FHIR_SERVER_ROOT
    cd ..	
    sudo chown -R $user_name:$user_group $FHIR_SERVER_ROOT
  popd
  gnome-terminal --tab -- /bin/bash -c "cd $FHIR_SERVER_ROOT; sudo -H -u $user_name bash -c './fhir_server_start.sh'; read"
}

function startFhirServer(){
  #startAsCustomUser
  
  gnome-terminal --tab -- /bin/bash -c "cd $FHIR_SERVER_ROOT; ./fhir_server_start.sh; read"
}

function startNetwork(){
  pushd $TEST_NETWORK
  ./start_network.sh
  popd
}

function startBackend(){
  gnome-terminal --tab -- /bin/bash -c "cd $BACKEND_APP_ROOT; ./backend_start.sh; read"
}

function startFrontend(){
  gnome-terminal --tab -- /bin/bash -c "cd $FRONTEND_APP_ROOT; npm start; read"
}

clear

startFhirServer
startNetwork
startBackend
startFrontend
