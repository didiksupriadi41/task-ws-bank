# WS-Bank

#### Deskripsi web service

WS-Bank adalah Web service yang digunakan untuk Aplikasi
[**Bank Pro**](https://gitlab.informatika.org/if3110-2019-02-k03-03/bank-pro)
dan [**Engima**](https://gitlab.informatika.org/if3110-2019-02-k03-03/engima).
Web service ini diimplementasikan diatas Java Servlet menggunakan
[**JAX-WS**](https://javaee.github.io/metro-jax-ws/) dengan protokol
[**SOAP**](https://www.w3.org/TR/2000/NOTE-SOAP-20000508/).

WS-Bank memiliki data yang terdiri dari data nasabah dan transaksi rekening
setiap bank.
Daftar data WS-Bank bisa dilihat di bagian
[**Basis data**](#basis-data-yang-digunakan)

WS-Bank menyediakan fungsi yang digunakan pada saat bertransaksi.
Daftar fungsi WS-Bank bisa dilihat di bagian
[**Pembagian tugas**](#pembagian-tugas-setiap-anggota)

#### Basis data yang digunakan

RDBMS yang digunakan adalah **MariaDB**, dengan skema table berikut:

![Screenshot](https://i.imgur.com/OjOQ9AT.png)

#### Pembagian tugas setiap anggota
|**No**|**Fungsi**|**NIM**|
|-|-|-|
|1|WS-Bank fungsi validasi no. rekening|[13517012](#johanes)|
|2|WS-Bank fungsi memberikan data rekening nasabah|[13517069](#didik-supriadi)|
|3|WS-Bank fungsi melakukan tranfer|[13517069](#didik-supriadi)|
|4|WS-Bank fungsi membuat akun virtual|[13517126](#louis-cahyadi)|
|5|WS-Bank fungsi mengecek transaksi dalam rentang waktu|[13517069](#didik-supriadi)|

#### Pembagian tugas setiap anggota
|**No**|**Tugas**|**NIM**|
|-|-|-|
|1|Code reviewer (PIC)|[13517069](#didik-supriadi)|
|2|CI/CD|[13517069](#didik-supriadi)|
|3|Eksplorasi dan *setup* mesin deployment|[13517069](#didik-supriadi)|

## Big thanks
* #### Asisten IF3110
* #### Johanes
* #### Didik Supriadi
* #### Louis Cahyadi
