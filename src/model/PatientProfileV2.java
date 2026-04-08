package model;

import interfaces.Confidential;
import interfaces.MedicalRecord;
import interfaces.Versioned;

import java.util.ArrayList;

public class PatientProfileV2 implements MedicalRecord, Versioned, Confidential {
    private String patientId;
    private String name;
    private String nik;
    private String diagnosa;
    private ArrayList<String> riwayatAlergi;
    private int securityLevel;

    public PatientProfileV2(String patientId, String name, String nik, String diagnosa, ArrayList<String> riwayatAlergi, int securityLevel) {
        this.patientId = patientId;
        this.name = name;
        this.nik = nik;
        this.diagnosa = diagnosa;
        this.riwayatAlergi = riwayatAlergi;
        this.securityLevel = securityLevel;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String getPatientName() {
        return name;
    }

    @Override
    public int getVersion() {
        return 2;
    }

    @Override
    public int getSecurityLevel() {
        return securityLevel;
    }

    @Override
    public void maskSensitiveData() {
        nik = "******";
        diagnosa = "******";
        for (int i = 0; i < riwayatAlergi.size(); i++) {
            riwayatAlergi.set(i, "******");
        }
    }

    @Override
    public String toString() {
        return "PatientProfileV2" +
                "\n  Patient ID     : " + patientId +
                "\n  Name           : " + name +
                "\n  NIK            : " + nik +
                "\n  Diagnosa       : " + diagnosa +
                "\n  Riwayat Alergi : " + riwayatAlergi +
                "\n  Version        : " + getVersion() +
                "\n  Sec Level      : " + securityLevel;
    }
}
