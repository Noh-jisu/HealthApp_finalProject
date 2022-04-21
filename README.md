# Final Project '나홀로GYM에'(현재 진행중)
- 멀티캠퍼스 앱개발 과정의 마지막단계 파이널 프로젝트
- 자유로운 주제로 앱&웹 개발 프로젝트
- 운동방법과 자세를 공유하고 나만의 루틴을 계획할 수 있는 앱 개발

<br>

## 목차
### 1. 담당업무
### 2. 개발환경
### 3. 개발과정 요점
### 4. 구현기능 화면구성
### 5. 문제점 및 오류해결
### 6. 느낀점 및 소감

<br>

## 1. 담당업무
- 게시판기능 구현
  1. 게시글 작성
  2. 게시글 목록
  3. 게시글 수정 및 삭제
  4. 댓글 작성 및 확인
  5. 조회수 및 좋아요 증가(중복확인)
  6. 게시글 페이징 기능 구현(Web 전용)
  7. firebase를 활용한 이미지 업로드 기능
  8. viewpager2를 활용한 이미지 슬라이드 기능(android)
  9. 함수를 활용하여 이미지 슬라이드 기능 구현(Web)
- Class Modeling
![image](https://user-images.githubusercontent.com/61276416/163956662-aea0f30b-64f0-4048-b9df-60ffda04ab47.png)
![image](https://user-images.githubusercontent.com/61276416/163956696-ee28c1c2-7077-464a-9473-00689b1e77ef.png)
![image](https://user-images.githubusercontent.com/61276416/163956734-03bdc1f1-ec08-4b3f-b366-811486806321.png)
![image](https://user-images.githubusercontent.com/61276416/163956758-8f3be4f2-738e-4be8-9d98-c64291eb0ff7.png)




<br>

## 2. 개발 환경 및 언어
```
- spring Boot 2.3.2
- Mybatis
- apache tomcat 9.0
- android
- Java
- Kotlin
- JavaScript
- HTML
- CSS
- Oracle DB
- firebase
```
<br>

## 3. 개발과정 요점
- 객체지향 언어의 특징과 장점을 이해하고 활용한다
- Android의 Activity가 최초로 실행 될 때를 고려해 Activity이동 시 전달할 데이터를 고려한다.
- 서버와 클라이언트의 데이터통신 순서를 생각하며 개발한다.
- Firebase Cloud를 이용한 이미지 업로드 기능을 구현한다.

<br>

## 4-1. 구현기능 화면구성(App)

<br>


- 게시판 목록 <br>
![image](https://user-images.githubusercontent.com/61276416/163952899-a8ad751f-874b-4eb5-ad4e-90da1d4dc056.png)

<br>

- 게시글 세부내용 <br>
![image](https://user-images.githubusercontent.com/61276416/163955673-a08f059b-01c7-460d-bb3c-b5e59d59465b.png)

<br>
1. 현재 파이어베이스 할당량 문제로 스토리지가 잠겨 이미지를 불러오지 못하지만, 결재 후 잠금을 풀면 이미지 불러오기 가능<br>
2. 댓글 작성 및 확인 가능

<br>
<br>

- 게시글 작성 <br>

![image](https://user-images.githubusercontent.com/61276416/163957996-148151a7-3ee3-49b9-bd28-c7cb5bf1da5a.png)

<br>

이미지 최대 5개까지 등록 가능

<br>

## 4-2. 구현기능 화면구성(Web)

<br>

- 게시판 목록 <br>
![image](https://user-images.githubusercontent.com/61276416/163957042-266adb55-20c0-4703-a8c2-883112e47723.png)

<br>

- 게시글 세부내용 <br>
![image](https://user-images.githubusercontent.com/61276416/163957182-eaded5dc-a63f-4a67-af25-ef9652657ac1.png)

<br>

- 게시글 작성 <br>
![image](https://user-images.githubusercontent.com/61276416/163957299-4fef9c73-5727-4fd4-8f94-2d87eddd7042.png)
![image](https://user-images.githubusercontent.com/61276416/163957346-305f6b3f-123c-4901-b8c7-dfa8c19503e9.png)

<br>

이미지 최대 5개까지 첨부 



## 5. 문제점 및 오류해결

<br>

### 1. 이미지 다중 업로드
 - 문제점 : 이미지를 최대 3개까지로 제한해 각각 버튼(이미지1, 이미지2, 이미지3)으로 하나씩 업로드하려했지만 번거로움의 문제 발생
 - 해결방법 : 업로드버튼을 하나만 만들고 이미지를 다중으로 선택할 수 있게한다. 선택된 이미지의 uri를 배열에 저장하고 해당 배열을 <br>
String형식으로 Oracle DB에 저장후 게시글 상세정보를 가져올때 다시 배열형식으로 만들어 for문을 통해 uri주소로 이미지를 노출시킨다.

<img width="430" alt="업로드버튼1개강조" src="https://user-images.githubusercontent.com/61276416/160230272-31cab6bf-4726-49f3-b732-000586d84f9f.png">


```

val b by lazy { ActivityWorkBbsWriteBinding.inflate(layoutInflater) }

    val REQUEST_CODE = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        // 사진선택 버튼 클릭시 이벤트(다중사진 업로드)
        b.imageUploadBtn.setOnClickListener {
            // 사진 다중선택 알림창 띄우기
            AlertDialog.Builder(this).setTitle("알림") // 제목
            .setMessage("사진을 길게 눌러 여러개를 선택할 수 있습니다.(최대 10개)")   // 메세지
            .setCancelable(false)   // 로그창 밖 터치해도 안꺼짐
            .setPositiveButton("사진선택하기"){ _, _ ->
                getImgFromGallery()
            }.show()
            // 사진선택 버튼 비활성화
            b.imageUploadBtn.visibility = View.INVISIBLE
            // 사진 다시선택 버튼 활성화
            b.WorkBbsReSelectImg.visibility = View.VISIBLE
        }
        // 다시선택 버튼 클릭시 이벤트(기존 업로드데이터 삭제 후 다시 다중업로드)
        b.WorkBbsReSelectImg.setOnClickListener {
            // 앞에 업로드 됐던 이미지 삭제
            deleteImg()
            // 이미지 리스트 클리어
            imgUriArr.clear()
            // 이미지 업로드 함수 실행
            getImgFromGallery()
        }
        // 작성하기 버튼 클릭시 이벤트
        b.WorkBbsWriteBtn.setOnClickListener {
            // 서버로 가져갈 데이터
            val id = LoginMemberDao.user?.id
            val nickname = LoginMemberDao.user?.nickname
            val title = b.WorkBbsWriteTitle.text.toString()
            val content = b.WorkBbsWriteContent.text.toString()
            val images = uriToString()

            // 서버로 데이터 전달 후 Toast 노출
            WorkBbsDao.getInstance().writebbs(
                WorkBbsDto(0, id, nickname, title, content,"",
                0, 0, 0, 0, 0, 0, images)
            )
            Toast.makeText(this,"작성이 완료되었습니다.", Toast.LENGTH_LONG).show()

            // 게시글 목록으로 이동
            val i = Intent(this, WorkBbsActivity::class.java)
            startActivity(i)
        }

        // 목록으로 버튼 클릭시 이벤트
        b.goToListBtn.setOnClickListener {
            // 목록으로 이동하는 알림창 띄우기
            AlertDialog.Builder(this).setTitle("알림") // 제목
                .setMessage("게시글 목록으로 돌아가시겠습니까??\n작성된 글은 저장되지 않습니다")   // 메세지
                .setCancelable(false)   // 로그창 밖 터치해도 안꺼짐
                .setPositiveButton("확인"){ _, _ ->   // 확인 누를시
                    if (imgUriArr != null){     // 업로드했던 이미지가 있으면 삭제
                        deleteImg()
                    }
                    // 게시글 목록으로 이동
                    val i = Intent(this, WorkBbsActivity::class.java)
                    startActivity(i)
                }.setNegativeButton("취소"){_, _ -> } // 취소 누를시 이벤트 없음
                .show()
        }

    }

    // 첨부할 사진 선택 시작함수(갤러리이동)
    fun getImgFromGallery() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    // 갤러리에서 선택한 사진들 파일 별 처리 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            // 다중 이미지 선택시
            if(data?.clipData != null) {
                val count = data.clipData?.itemCount

                if(count!! > 10) {
                    Toast.makeText(applicationContext, "사진은 최대 10장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                    return
                }
                for( i in 0 until count!!) {
                    val imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    //permissionLauncher(imageUri)
                    permissionLauncher(imageUri)
                }
            } else if (data?.data != null) {    // 단일 이미지 선택시
                var imageUri: Uri = data.data!!
                permissionLauncher(imageUri)
            }
        }
    }
    // 업로드한 이미지들
    var imgUriArr = arrayListOf<String>()
    // 로그인한 유저 아이디
    val userId = LoginMemberDao.user?.id
    // 스토리지 버킷 연결 코드
    val storage = Firebase.storage("gs://healthapp-client.appspot.com")

    // 권한 요청 런처
    fun permissionLauncher(uri: Uri){
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(!isGranted) {
                Toast.makeText(baseContext, "외부 저장소 읽기 권한을 승인해야 사용할 수 있습니다", Toast.LENGTH_LONG).show()
            } else {
                uploadImage(uri)
            }
        }
        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    // 이미지 업로드 함수
    fun uploadImage(uri: Uri) {
        // 1. 경로 + 사용자ID + 밀리초로 파일주소 만들기
        val fullPath = makeFilePath("images", userId!!, uri)
        // 2. 스토리지에 저장할 경로 설정
        val imageRef = storage.getReference(fullPath)
        // 3. 업로드 태스크 생성
        val uploadTask = imageRef.putFile(uri)

        // 4. 업로드 실행 및 결과 확인
        uploadTask.addOnFailureListener{
            Log.d("스토리지", "실패=>${it.message}")
        }.addOnSuccessListener { taskSnapshot ->
            Log.d("스토리지", "성공 주소=>${fullPath}")     // 5. 경로를 DB에 저장하고 사용
        }
    }

    // 파일 전체 경로생성 함수
    fun makeFilePath(path:String, userId:String, uri:Uri) : String {
        // 마임타입 예) images/jpeg
        val mimeType = contentResolver.getType(uri)?:"none"
        // 확장자 예) jpeg
        val ext = mimeType.split("/")[1]
        // 시간값 예) 1232131241312
        val timeSuffix = System.currentTimeMillis()
        // 완성
        val filename = "${path}/${userId}_${timeSuffix}.${ext}"  // 예) 경로/사용자ID_1232131241312.jpeg

        imgUriArr.add(filename)
        b.WorkSelectedImg.text = imgUriArr[0] + "..."
        return filename
    }

    // uri 주소 String 형식으로 전환 함수
    fun uriToString() : String{
        var imgs = ""
        if(imgUriArr.size == 1){
            imgs = imgUriArr[0]
        }else if(imgUriArr.size > 1){
            imgs = imgUriArr.joinToString(separator = ",", limit = imgUriArr.size)
        }
        println(imgs)
        return imgs
    }

    // 업로드된 이미지 삭제 함수
    fun deleteImg(){
        val storageRef = storage.reference
        for(i in imgUriArr){
            val desertRef = storageRef.child(i)
            desertRef.delete().addOnSuccessListener {
                println("파일삭제 성공")
            }.addOnFailureListener{
                println("파일삭제 실패")
            }
        }

    }

}
```

<br>

### 2. Activity 에서 Activity를 이동할때 원하는 프래그먼트를 띄운상태로 이동이 안되는 문제
![image](https://user-images.githubusercontent.com/61276416/163952899-a8ad751f-874b-4eb5-ad4e-90da1d4dc056.png)

<br>
- 문제점 : 해당 사진은 게시글 목록 사진이다. 하단 네비게이션바의 두번째에 위치해 있는 fragment인데 게시글 세부내용이나 글 작성후에 startActivity를 통해 4개의 프래그먼트를 띄우고있는 Activity로 이동 시, 첫번째 화면인 운동목록을 띄워주는 문제점이 생겼다.
- 해결방법 : companion object의 int형 변수를 지정하고, 각 프래그먼트가 해당하는 고유의 int값을 만들어 해당 값이 일치할 경우에는 기본으로 띄워줄 fragment화면을 if문을 통해 지정해줄 수 있다. 

<br>

```
val fragmentTransaction = fm.beginTransaction()

    if(selectedFragment == 0){
        fragmentTransaction.add(R.id.frView, WorklistFragment(this, applicationContext))
    }else if(selectedFragment == 1){
        fragmentTransaction.add(R.id.frView, BbsFragment(this))
    }else if(selectedFragment == 2){
        fragmentTransaction.add(R.id.frView, CalendarFragment(this))
    }else if(selectedFragment == 3){
        fragmentTransaction.add(R.id.frView, MypageFragment(this))
    }else{
        fragmentTransaction.add(R.id.frView, WorklistFragment(this, applicationContext))
    }
    fragmentTransaction.commit()
```

<br>

### 3. 웹페이지 이미지 업로드 시간문제
- 문제점 : firebase에 이미지를 업로드하는 시간이 다소 길었는데, 이 과정에서 게시글을 등록해버리면 정상적으로 이미지가 업로드되지 않고 스킵되는 현상 발생.
- 해결방법 : JavaScript의 setTimeout() 함수를 이용해, 다음에 이뤄질 코드를 지연시키고, 사용자가 다음 동작을 진행할 수 없도록 로딩화면을 만들어 업로드 및 DB에 정상적으로 저장 후 다음 이벤트를 진행할 수 있도록 코드를 구성

## 6. 느낀점 및 소감
- 첫번째 프로젝트때 하지 못했던 이미지업로드 기능을 구현했다.
- 이번프로젝트를 진행하면서 android의 기본 옵션인 recyclerView에 대한 이해가 확실히 이전보다 높아졌고, nestedscrollview와 linearlayout을 통해서 화면을 구성하는 View를 배치시키는 방법을 배웠다. 
- 프로젝트가 커질수 록 초기 기획및 설계단계가 정말 중요하다는 사실을 깨달았다. 중간중간 추가되는 DB테이블이나 칼럼도 많았고, 조회수 및 좋아요 중복체크기능 부분도 중간에 추가되었다. 경험의 차이일 순 있지만, 다음 프로젝트때는 초반 설계를 좀더 구체적이고 확실하게 계획하여, 중간 변동사항이나 문제점이 발생하지 않도록 주의해야겠다.
