package com.auth0.infinitelist;

class NetworkState {

    private Status status;

    NetworkState(Status status){
        this.status = status;
    }

    Status getStatus() {
        return status;
    }

}

enum Status {
    LOADING,
    SUCCESS,
    FAILED
}
