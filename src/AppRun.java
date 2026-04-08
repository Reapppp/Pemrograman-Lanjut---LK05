import gateway.IntegrationGateway;
import gateway.SecureResponse;
import model.ClaimHistoryV1;
import model.PatientProfileV1;
import model.PatientProfileV2;

import java.util.ArrayList;

public class AppRun {
    public static void main(String[] args) {

        System.out.println("\n=== MediGuard Integration Gateway - Simulasi ===\n");

        // -------------------------------------------------------
        // Simulasi 1: PatientProfileV1 - Dokter akses RENDAH
        // clearance=1, data security=2 (RESTRICTED) -> masking
        // -------------------------------------------------------
        System.out.println("--- Simulasi 1: PatientV1, Dokter Akses Rendah (clearance=1) ---");

        PatientProfileV1 pasien1 = new PatientProfileV1(
            "P001", "Budi Santoso", "3578012345670001", "Diabetes Melitus", 2
        );
        IntegrationGateway<PatientProfileV1> gateway1 = new IntegrationGateway<>(pasien1);
        SecureResponse<PatientProfileV1> resp1 = gateway1.fetchData("P001", 1);
        System.out.println(resp1);

        // -------------------------------------------------------
        // Simulasi 2: PatientProfileV1 - Dokter akses TINGGI
        // clearance=3, data security=2 (RESTRICTED) -> akses penuh
        // -------------------------------------------------------
        System.out.println("\n--- Simulasi 2: PatientV1, Dokter Akses Tinggi (clearance=3) ---");

        PatientProfileV1 pasien2 = new PatientProfileV1(
            "P001", "Budi Santoso", "3578012345670001", "Diabetes Melitus", 2
        );
        IntegrationGateway<PatientProfileV1> gateway2 = new IntegrationGateway<>(pasien2);
        SecureResponse<PatientProfileV1> resp2 = gateway2.fetchData("P001", 3);
        System.out.println(resp2);

        // -------------------------------------------------------
        // Simulasi 3: PatientProfileV2 - Dokter akses RENDAH
        // clearance=1, data security=2 (RESTRICTED) -> masking
        // -------------------------------------------------------
        System.out.println("\n--- Simulasi 3: PatientV2, Dokter Akses Rendah (clearance=1) ---");

        ArrayList<String> alergi = new ArrayList<>();
        alergi.add("Penisilin");
        alergi.add("Aspirin");

        PatientProfileV2 pasien3 = new PatientProfileV2(
            "P002", "Siti Rahayu", "3578019876540002", "Hipertensi", alergi, 2
        );
        IntegrationGateway<PatientProfileV2> gateway3 = new IntegrationGateway<>(pasien3);
        SecureResponse<PatientProfileV2> resp3 = gateway3.fetchData("P002", 1);
        System.out.println(resp3);

        // -------------------------------------------------------
        // Simulasi 4: PatientProfileV2 - Dokter akses TINGGI
        // clearance=3, data security=2 (RESTRICTED) -> akses penuh
        // -------------------------------------------------------
        System.out.println("\n--- Simulasi 4: PatientV2, Dokter Akses Tinggi (clearance=3) ---");

        ArrayList<String> alergi2 = new ArrayList<>();
        alergi2.add("Penisilin");
        alergi2.add("Aspirin");

        PatientProfileV2 pasien4 = new PatientProfileV2(
            "P002", "Siti Rahayu", "3578019876540002", "Hipertensi", alergi2, 2
        );
        IntegrationGateway<PatientProfileV2> gateway4 = new IntegrationGateway<>(pasien4);
        SecureResponse<PatientProfileV2> resp4 = gateway4.fetchData("P002", 3);
        System.out.println(resp4);

        // -------------------------------------------------------
        // Simulasi 5: ClaimHistoryV1 - bukti ekstensibilitas
        // Gateway yang sama bisa digunakan tanpa perubahan apapun
        // -------------------------------------------------------
        System.out.println("\n--- Simulasi 5: ClaimHistoryV1, Dokter Akses Rendah (clearance=1) ---");

        ClaimHistoryV1 klaim1 = new ClaimHistoryV1(
            "P003", "Ahmad Fauzi", "CLM-2024-001", "Gagal Ginjal", 2
        );
        IntegrationGateway<ClaimHistoryV1> gateway5 = new IntegrationGateway<>(klaim1);
        SecureResponse<ClaimHistoryV1> resp5 = gateway5.fetchData("P003", 1);
        System.out.println(resp5);

        // -------------------------------------------------------
        // Simulasi 6: Patient ID tidak ditemukan
        // -------------------------------------------------------
        System.out.println("\n--- Simulasi 6: Patient ID tidak ditemukan ---");

        PatientProfileV1 pasien6 = new PatientProfileV1(
            "P001", "Budi Santoso", "3578012345670001", "Diabetes Melitus", 2
        );
        IntegrationGateway<PatientProfileV1> gateway6 = new IntegrationGateway<>(pasien6);
        SecureResponse<PatientProfileV1> resp6 = gateway6.fetchData("P999", 3);
        System.out.println(resp6);

        System.out.println("\n=== Simulasi Selesai ===");
    }
}
