# POSPayment-CaseStudy
Tek Activity ve iki Fragment kullanarak QR ile ödeme alma, onaylama ve veritabanına kaydetme uygulaması.

## Kullanılan Teknolojiler ve Mimariler
MVVM, Retrofit, Dagger, Room, Fragment, Truth, Mockito, Espresso

## Kullanım
Uygulama açıldığında MainActivity POSFragment'ı açar.
Kullanıcı burada ödeme tutarını girer ve "QR ile Öde" tuşuna basar.
Ödeme tutarı OSY-QR servisine yollar.
Gelen cevaba göre QR kodu çizer ve gösterir.
Bu işlem sonucunda "Arayüze Git" tuşu aktifleşir.

CustomerInfoFragment açılır ve müşteri arayüzüne gidilmiş olur.
Son yapılan ödeme tutarı ve bu ödemenin QR kodu gösterilir.
"Ödemeyi Onayla" tuşuna basarak ödeme miktarını ve QR kodu OSY-QR servisine yollar.
Servis, ödemenin onaylandığına dair cevap döndüğünde onaylama tuşu deaktif hale gelir ve tik resmi gözükür.
Ödemenin onaylanmasıyla beraber bu ödeme veritabanına kaydedilir.
Bu ödeme ve geçmişteki ödemeler ekranın aşağısında bulunan listede gözükmektedir.

## Kodların Genel Açıklamaları
### API
#### Request
##### PaymentAction
Ödeme yollanırken PostPayment içinde bulunabilir. (Projede kullanılmasına gerek yok. Sebep: "If returnCode != 1000, “paymentInfoList” is not required.")
##### PaymentInfo
Ödeme yollanırken PostPayment içinde bulunabilir. (Projede kullanılmasına gerek yok. Sebep: "If returnCode != 1000, “paymentInfoList” is not required.")
##### PostPayment
Ödeme isteği atılırken kullanılan class
##### QRForSale
QRForSale isteği atılırken kullanılan class
#### Response
##### PaymentResponse
Gönderilen ödeme isteğine karşılık servisten alınan cevap
##### QRForSaleResponse
Gönderilen QR isteğine karşılık servisten alınan cevap
### OSYInterceptor
Servise gönderilen HTTP isteklerine yetkilendirme headerlarını ekleyen interceptor
### OSYServis
Backend servisle haberleşmeyi sağlayan servis
### Base
#### BaseViewModel
Base View Model sınıfı. OSY Servisi ve database repository objelerini Dagger yoluyla alıp tutar. String ile QR Bitmap oluşturma fonksiyonunu içerir. Aynı zamanda view model'daki LiveData'ların tek seferliğine observe edilmesini sağlayan extension da buradadır.
### Components
#### AppComponent
Dependency injection yapan Dagger'ın komponentidir. DI işlemlerini bu interface yapar. Kullandığı modüller @Component içinde belirtilmiştir. BaseViewModel'a enjekte eden budur.
### CustomerInfo
#### CustomerInfoFragment
Uygulamanın müşteri arayüzü kısmını oluşturur. View binding aracılığıyla layoutu kontrol eder. Ödenecek tutarı ve QR bilgisini POSFragment'tan parametre olarak alır.
#### CustomerInfoViewModel
Müşteri arayüzü ekranı için oluşturulmuş view model. Fragment için veritabanına erişim sunar. Aynı zamanda ödeme onaylanması için servisi tetikler.
#### CustomerInfoViewModelFactory
CustomerInfoViewModel oluşturmak için kullanılan factory patterni temelli class.
#### HistoryRecyclerViewAdapter
Geçmişteki ödemeleri göstermek için Recycler Viewlar kullanan adaptör.
### DB.Payment
#### Payment
Ödeme veritabanındaki satırları temsil eden data class
#### PaymentDAO
Ödeme veritabanındaki işlemleri yürüten DAO (Data Access Object)
#### PaymentDatabase
Ödeme veritabanını temsil eden class. İçinde nasıl satırlar olduğunu @Database(entities) aracılığıyla alır. Versiyonlar veritabanındaki değişikliklerin takip edilmesi ve gerektiğinde Migration fonksiyonları yazılıp uygulanmasını sağlar.
#### PaymentRepository
Ödeme veritabanındaki işlemleri DAO üzerinde tetikleyen class
### Main
#### MainActivity
Fragmentları içinde barındırır ve ilk fragment olan POSFragment'ı açar.
### Modules
#### DatabaseModule
Dependency injection sırasında gerekli olan veritabanıyla ilgili değişkenleri sağlayan class. @Singleton bu değişkenin sadece bir defa oluşturulmasını sağlar.
#### NetworkModule
Backend ile iletişimle ilgili olan değişkenleri DI için sağlar. SSL sertifika doğrulama işlemi etkisiz hale getirilmiştir. Http üzerinden yapılan haberleşmelerin loglanması için HttpLoggingInterceptor eklenmiştir.
### POS
#### POSFragment
Uygulamanın ödeme alma kısmını oluşturur. Ödeme alındıktan sonra müşteri arayüzünü açmak için CustomerInfoFragment'ı ödeme miktarı ve QR verisiyle başlatır.
#### POSViewModel
Fragmentta girilen ödeme miktarı için servise istek atar ve gelen isteğe göre QR bitmapini oluşturur.
#### POSViewModelFactory
POSViewModel oluşturmak için kullanılan factory patterni temelli class.