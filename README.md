# KaKao-Application
카카오 API를 사용하여 Daum-이미지 검색 예제

🚀 **ScreenShot**
-----------
![스크린샷 2021-04-01 오후 5 41 36](https://user-images.githubusercontent.com/40010002/113267674-96489b80-9311-11eb-9dfe-08982ab527f1.png)

⚡️ **Project Structure**
-----------------
<img width="254" alt="스크린샷 2021-04-01 오후 5 31 07" src="https://user-images.githubusercontent.com/40010002/113266293-15d56b00-9310-11eb-8642-e76aa256e9cc.png">

💡 **Stack & Libraries** 
--------------------        
- Minimum SDK level 23 / Target API 30
- **Kotlin** based + **RxJava** for asynchronous.
- **Dagger-Hilt** - dependency injection
- **ViewBinding** - for binding
- **Retrofit2** - REST APIs
- **OkHttp3** - implementing interceptor.   
- **Glide** - loading images
- **JetPack** 
  - **LiveData** - notify domain layer data to views.
  - **Lifecycle** - dispose of observing data when lifecycle state changes.
  - **ViewModel** - UI related data holder, lifecycle aware.

💎 **Architecture**
-------------------
**MVVM** based
![image](https://user-images.githubusercontent.com/40010002/113266203-f5a5ac00-930f-11eb-99f1-b57036a15f28.png)
