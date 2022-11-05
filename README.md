# POSPayment-CaseStudy
Tek Activity ve iki Fragment kullanarak QR ile ödeme alma, onaylama ve veritabanına kaydetme uygulaması.
<br>
## Kullanılan Teknolojiler ve Mimariler
MVVM, Retrofit, Dagger, Room, Fragment, Truth, Mockito, Espresso
<br>
## Kullanım
Uygulama açıldığında MainActivity POSFragment'ı açar.<br>
Kullanıcı burada ödeme tutarını girer ve "QR ile Öde" tuşuna basar.<br>
Ödeme tutarı OSY-QR servisine yollanır.<br>
Gelen cevaba göre QR kodu çizer ve gösterir.<br>
Bu işlem sonucunda "Arayüze Git" tuşu aktifleşir.<br>
<br>
Tuşa basıldığında CustomerInfoFragment açılır ve müşteri arayüzüne gidilmiş olur.<br>
Son yapılan ödeme tutarı ve bu ödemenin QR kodu gösterilir.<br>
"Ödemeyi Onayla" tuşuna basarak ödeme miktarını ve QR kodu OSY-QR servisine yollar.<br>
Servis, cevap döndüğünde onaylama tuşu deaktif hale gelir ve tik resmi gözükür.<br>
Ödemenin onaylanmasıyla beraber bu ödeme veritabanına kaydedilir.<br>
Bu ödeme ve geçmişteki ödemeler ekranın aşağısında bulunan listede gözükmektedir.<br>
<br>
## Testler
### Instrumentation Test
#### MainActivityTest
Fragmentların çalışma akışlarını ve doğru değerlerin gösterilip gösterilmediğini kontrol eden testler.<br>
testPOSFragmentFlow: POS arayüzünün normal kullanım testi<br>
testCustomerInfoFragmentReceiptAmount: POS arayüzünde girilen miktarın müşteri arayüzünde doğru gösterildiğini kontrol eder<br>
testCustomerInfoFragmentPaymentHistory: Onaylanan ödemenin doğru bir şekilde geçmişe kaydedildiğini kontrol eder<br>
### Unit Test
#### POSViewModelTest
verifyReceiptAmount: Ödeme miktarının QR isteği atıldıktan sonra doğru kaldığını kontrol eder<br>
#### CustomerInfoViewModelTest
verifyPaymentAdd: Ödemenin veritabanına doğru kaydedildiğini test eder<br>
verifyPaymentResponse: Ödeme isteğine karşılık beklenen cevabın döndüğünü test eder<br>
## Kodların Genel Açıklamaları
### API
#### Request
##### PaymentAction
Ödeme yollanırken PostPayment içinde bulunabilir. (Projede kullanılmasına gerek yok. Sebep: "If returnCode != 1000, “paymentInfoList” is not required.")<br>
##### PaymentInfo
Ödeme yollanırken PostPayment içinde bulunabilir. (Projede kullanılmasına gerek yok. Sebep: "If returnCode != 1000, “paymentInfoList” is not required.")<br>
##### PostPayment
Ödeme isteği atılırken kullanılan class<br>
##### QRForSale
QRForSale isteği atılırken kullanılan class<br>
#### Response
##### PaymentResponse
Gönderilen ödeme isteğine karşılık servisten alınan cevap<br>
##### QRForSaleResponse
Gönderilen QR isteğine karşılık servisten alınan cevap<br>
### OSYInterceptor
Servise gönderilen HTTP isteklerine yetkilendirme headerlarını ekleyen interceptor<br>
### OSYServis
Backend servisle haberleşmeyi sağlayan servis<br>
### Base
#### BaseViewModel
Base View Model sınıfı. OSY Servisi ve database repository objelerini Dagger yoluyla alıp tutar. String ile QR Bitmap oluşturma fonksiyonunu içerir. Aynı zamanda view model'daki LiveData'ların tek seferliğine observe edilmesini sağlayan extension da buradadır.<br>
#### BaseFragment
Base Fragment sınıfı. ViewBinding ve BaseViewModel'a göre fragmentı hazırlar. View bindingin kurulmasını ve gerekli Base View Model'in oluşturulmasını sağlar.
### Components
#### AppComponent
Dependency injection yapan Dagger'ın komponentidir. DI işlemlerini bu interface yapar. Kullandığı modüller @Component içinde belirtilmiştir. BaseViewModel'a enjekte eden budur.<br>
### CustomerInfo
#### CustomerInfoFragment
Uygulamanın müşteri arayüzü kısmını oluşturur. View binding aracılığıyla layoutu kontrol eder. Ödenecek tutarı ve QR bilgisini POSFragment'tan parametre olarak alır.<br>
#### CustomerInfoViewModel
Müşteri arayüzü ekranı için oluşturulmuş view model. Fragment için veritabanına erişim sunar. Aynı zamanda ödeme onaylanması için servisi tetikler.<br>
#### HistoryRecyclerViewAdapter
Geçmişteki ödemeleri göstermek için Recycler Viewlar kullanan adaptör.<br>
### DB.Payment
#### Payment
Ödeme veritabanındaki satırları temsil eden data class<br>
#### PaymentDAO
Ödeme veritabanındaki işlemleri yürüten DAO (Data Access Object)<br>
#### PaymentDatabase
Ödeme veritabanını temsil eden class. İçinde nasıl satırlar olduğunu @Database(entities) aracılığıyla alır. Versiyonlar veritabanındaki değişikliklerin takip edilmesi ve gerektiğinde Migration fonksiyonları yazılıp uygulanmasını sağlar.<br>
#### PaymentRepository
Ödeme veritabanındaki işlemleri DAO üzerinde tetikleyen class<br>
### Main
#### MainActivity
Fragmentları içinde barındırır ve ilk fragment olan POSFragment'ı açar.<br>
#### ViewModelFactory
View modelları factory patterni ile oluşturur.<br>
### Modules
#### DatabaseModule
Dependency injection sırasında gerekli olan veritabanıyla ilgili değişkenleri sağlayan class. @Singleton bu değişkenin sadece bir defa oluşturulmasını sağlar.<br>
#### NetworkModule
Backend ile iletişimle ilgili olan değişkenleri DI için sağlar. SSL sertifika doğrulama işlemi etkisiz hale getirilmiştir. Http üzerinden yapılan haberleşmelerin loglanması için HttpLoggingInterceptor eklenmiştir.<br>
### POS
#### POSFragment
Uygulamanın ödeme alma kısmını oluşturur. Ödeme alındıktan sonra müşteri arayüzünü açmak için CustomerInfoFragment'ı ödeme miktarı ve QR verisiyle başlatır.<br>
#### POSViewModel
Fragmentta girilen ödeme miktarı için servise istek atar ve gelen isteğe göre QR bitmapini oluşturur.<br>
