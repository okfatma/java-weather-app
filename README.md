## Projeyi çalıştırabilmek için kurulu olması gerekenler
1. [docker](https://docs.docker.com/get-docker/) 

## Projeyi çalıştırmak için 
- Git bash kullanıyorsanız proje dizinindeyken
1. `cp .env .env.example` 
2. `docker compose --env-file .env up -d`
- Windows
1. Projeyi indirdiğiniz klasördeki .env.example dosyasını .env olacak şekilde kopyalayıp içindeki değerleri güncelleyiniz.
2. cmd veya terminal açtıktan sonra `docker compose --env-file .env up -d` yazarak docker versiyonunda çalıştırabilirsiniz.

Docker çalıştıktan sonra [http://localhost:4200](localhost:4200) adresine girebilirsiniz.