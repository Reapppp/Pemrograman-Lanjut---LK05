# MediGuard Integration Gateway

## Deskripsi Project

Project ini dibuat untuk memenuhi tugas **Latihan Kerja 05 (LK05)** tentang **Generic Class** dalam mata kuliah Pemrograman Lanjut.

Studi kasusnya adalah sistem integrasi antara **PT Asuransi BPJS** dan **Rumah Sakit Mitra Keluarga**. Masalah utamanya adalah bagaimana membuat satu sistem yang bisa menangani berbagai jenis data medis sekaligus menjaga keamanan data pasien berdasarkan level akses dokter yang meminta data tersebut.

---

## Struktur Project

```
src/
├── interfaces/
│   ├── MedicalRecord.java      # Kontrak dasar semua data medis
│   ├── Versioned.java          # Kontrak untuk versi data
│   └── Confidential.java       # Kontrak untuk keamanan data
├── model/
│   ├── PatientProfileV1.java   # Data pasien versi 1
│   ├── PatientProfileV2.java   # Data pasien versi 2 (ada riwayat alergi)
│   └── ClaimHistoryV1.java     # Data klaim asuransi
├── gateway/
│   ├── SecureResponse.java     # Pembungkus hasil respons
│   └── IntegrationGateway.java # Inti sistem gateway
└── AppTun.java                 # Simulasi utama
```

---

## Cara Kerja Sistem

### 1. Interface sebagai Kontrak

Sistem ini punya 3 interface utama yang harus diimplementasikan oleh semua data medis:

- **MedicalRecord** — memastikan setiap data punya ID dan nama pasien
- **Versioned** — memastikan setiap data punya nomor versi
- **Confidential** — memastikan setiap data punya level keamanan dan bisa melakukan *masking* pada field sensitifnya sendiri

### 2. Generic Class dan Multiple Bounds

`IntegrationGateway` dibuat sebagai **generic class** dengan batasan tipe:

```java
public class IntegrationGateway<T extends MedicalRecord & Versioned & Confidential>
```

Artinya, tipe data apapun yang masuk ke Gateway **wajib** memenuhi ketiga kontrak interface di atas. Ini membuat sistem aman sejak tahap kompilasi, kalau ada tipe data yang tidak lengkap, program langsung error sebelum dijalankan.

### 3. Sistem Masking Data Sensitif

Ketika dokter meminta data dengan level akses yang tidak mencukupi, Gateway akan otomatis menyembunyikan field sensitif seperti NIK dan Diagnosa. Cara kerjanya:

```java
if (clearanceLevel < record.getSecurityLevel()) {
    record.maskSensitiveData(); // tiap kelas masking fieldnya sendiri
    return new SecureResponse<>(true, record, "Akses terbatas. Data sensitif disembunyikan.");
}
```

Gateway **tidak perlu tahu** data apa yang sedang diproses (V1, V2, atau ClaimHistory). Gateway cukup memanggil `maskSensitiveData()`, dan masing-masing kelas akan menangani maskingnya sendiri. Konsep ini disebut **polimorfisme**.

### 4. Mudah Dikembangkan

Karena sistem berbasis interface dan generic, kalau di masa depan ada tipe data baru (misal `ClaimHistoryV2`), cukup buat kelas baru yang mengimplementasikan ketiga interface tadi. **Kode Gateway tidak perlu diubah sama sekali.**

---

## Level Keamanan

| Level | Keterangan |
|-------|------------|
| 1 | PUBLIC — semua dokter bisa lihat data lengkap |
| 2 | RESTRICTED — butuh clearance minimal level 2 |
| 3 | SECRET — hanya clearance level 3 yang bisa lihat penuh |

---

## Cara Menjalankan

```bash
# Compile
javac -d out src/interfaces/*.java src/model/*.java src/gateway/*.java src/AppRun.java

# Jalankan
java -cp out AppRun
```

---

## Simulasi yang Dijalankan

| Simulasi | Data | Clearance Dokter | Hasil |
|----------|------|-----------------|-------|
| 1 | PatientProfileV1 | 1 (Rendah) | NIK & Diagnosa ter-*masking* |
| 2 | PatientProfileV1 | 3 (Tinggi) | Data tampil lengkap |
| 3 | PatientProfileV2 | 1 (Rendah) | NIK, Diagnosa & Alergi ter-*masking* |
| 4 | PatientProfileV2 | 3 (Tinggi) | Data tampil lengkap |
| 5 | ClaimHistoryV1   | 1 (Rendah) | Nomor Klaim & Diagnosa ter-*masking* |
| 6 | Patient ID salah | 3 (Tinggi) | Data tidak ditemukan |

---

## Konsep yang Diterapkan

- **Generic Class** dengan multiple bounds (`extends A & B & C`)
- **Polimorfisme** — masking dilakukan oleh masing-masing kelas tanpa `instanceof` di Gateway
- **Prinsip DRY** — satu Gateway untuk semua tipe data
- **Compile-time Safety** — kesalahan tipe terdeteksi sebelum program dijalankan
