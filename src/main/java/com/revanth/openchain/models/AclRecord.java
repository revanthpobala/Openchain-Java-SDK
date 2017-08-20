package com.revanth.openchain.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class AclRecord {

    /**
     * TODO : Find a way to set the access controls.
     */
    @JsonProperty("subjects")
    public List<AclSubject> subjects;

    @JsonProperty("permissions")
    public Map<String, String> permissions;

    @JsonProperty("recursive")
    public boolean recursive;

    @JsonProperty("record_name")
    public String recordName;

    @JsonProperty("record_name_matching")
    public String recordNameMatching;

    public AclRecord() {
        List<AclSubject> subjects = new ArrayList<AclSubject>();

    }

    public List<AclSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<AclSubject> subjects) {
        this.subjects = subjects;
    }

    public Map<String, String> getpermissions() {
        return permissions;
    }

    public void setpermissions(Map<String, String> permissions) {
        permissions = permissions;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordNameMatching() {
        return recordNameMatching;
    }

    public void setRecordNameMatching(String recordNameMatching) {
        this.recordNameMatching = recordNameMatching;
    }


    public AclRecord allowCreate(boolean allow){
        allow = true;
        allowPermission("account_create", allow);
        return this;
    }

    private void allowPermission(String name, boolean allow) {
        if(permissions.containsKey(name)){
            permissions.remove(name);
        }
        permissions.put(name, null);
    }

}
