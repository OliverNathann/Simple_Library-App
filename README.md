# Simple Library App

Simple Library App adalah aplikasi perpustakaan sederhana yang dibangun menggunakan **Java Spring Boot** sebagai backend dan **HTML/CSS** sebagai frontend.

## 📌 Fitur Utama
- **Manajemen Buku**: Tambah, edit, dan hapus buku di perpustakaan.
- **Manajemen Pengguna**: Registrasi, login, dan autentikasi pengguna.
- **Peminjaman Buku**: Pengguna dapat meminjam buku dan mengembalikannya.
- **Cek Keterlambatan**: Admin dapat melihat daftar buku yang terlambat dikembalikan.

## 🛠️ Teknologi yang Digunakan
- **Backend**: Java Spring Boot
- **Frontend**: HTML, CSS
- **Database**: H2 Database / MySQL
- **Keamanan**: JWT Authentication

## 🚀 Cara Menjalankan Aplikasi
### 1. Clone Repository
```bash
git clone https://github.com/OliverNathann/Simple_Library-App.git
cd Simple_Library-App
```

### 2. Jalankan Backend (Spring Boot)
- Pastikan **Java 17+** telah terinstal.
- Konfigurasi **application.properties** sesuai dengan database yang digunakan.
```bash
mvn spring-boot:run
```
Aplikasi backend akan berjalan di `http://localhost:8080`.

### 3. Jalankan Frontend (HTML/CSS)
Buka file `index.html` di browser atau gunakan ekstensi **Live Server** di VSCode.

## 📡 API Endpoint
| Method | Endpoint | Deskripsi |
|--------|------------|------------|
| POST | `/auth/register` | Register pengguna baru |
| POST | `/auth/login` | Login dan mendapatkan JWT |
| GET | `/books` | Mendapatkan daftar buku |
| POST | `/books` | Menambahkan buku (Admin) |
| POST | `/borrow` | Meminjam buku |
| POST | `/return/{loanId}` | Mengembalikan buku |
| GET | `/overdue` | Menampilkan daftar buku yang overdue |

## 📷 Tampilan Aplikasi
![Register Admin](https://github.com/user-attachments/assets/ef0cd71d-e363-4ec1-a171-c592ba781078)
![Register User](https://github.com/user-attachments/assets/47ef1390-7dde-473c-ae80-816247c94d95)
![Login Admin](https://github.com/user-attachments/assets/442371de-967c-44da-a781-a1fcc2be8278)
![Login User](https://github.com/user-attachments/assets/bb3246cc-3506-4eed-b5e1-6434d8d2569c)
![Add Booke  Role Admin](https://github.com/user-attachments/assets/bc4f2933-ad1e-4d42-bfb3-886cbf017378)
![Book Borrow](https://github.com/user-attachments/assets/6d5a1ef2-9a16-4f83-bde2-49824a716512)
![Return Book](https://github.com/user-attachments/assets/d1b2d273-c675-4e19-bb86-c6ef7770aaf4)
![Overdue Check  Role Admin](https://github.com/user-attachments/assets/593f6b72-17c9-4885-9bb9-3644f7cc6b22)
![Tampilan Web](https://github.com/user-attachments/assets/4ef6ac93-5b2b-4263-b806-811241179a64)

## Posman Collection
https://drive.google.com/file/d/14ebX6QGEqpZYoAVQ0raO5tkwpnfNK0_3/view?usp=sharing


## 👥 Kontribusi
Pull request selalu diterima! Pastikan untuk mendiskusikan perubahan yang ingin Anda lakukan terlebih dahulu melalui issue.

## 📝 Lisensi
Proyek ini menggunakan lisensi **MIT**.

---
📌 **Developer**: Oliver Nathan

