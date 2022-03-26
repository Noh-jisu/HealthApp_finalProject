# Final Project '나홀로GYM에'(현재 진행중)
- 멀티캠퍼스 앱개발 과정의 마지막단계 파이널 프로젝트
- 자유로운 주제로 앱&웹 개발 프로젝트
- 운동방법과 자세를 공유하고 나만의 루틴을 계획할 수 있는 앱 개발

<br>

## 목차
### 1. 담당업무
### 2. 개발환경
### 3. 구현기능 화면구성
### 4. 문제점 및 오류해결
### 5. 느낀점 및 소감

<br>

## 1. 담당업무
- 게시판기능 구현
- 페이징 기능 구현(Web 전용)
- firebase를 활용한 이미지 업로드기능 구현
- Class Modeling
<img width="944" alt="KakaoTalk_20220326_143238319" src="https://user-images.githubusercontent.com/61276416/160229948-c498b7f0-c8e0-44b7-be2a-70c4e00a6222.png">
<img width="944" alt="KakaoTalk_20220326_143249418" src="https://user-images.githubusercontent.com/61276416/160229951-8ddc43cf-5c73-48ff-affc-075c555b6401.png">


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

## 3. 구현기능 화면구성

<br>


- 게시판 목록
<img width="428" alt="KakaoTalk_20220326_142755351" src="https://user-images.githubusercontent.com/61276416/160229883-620b64ec-d8db-4e7c-8405-82ac5de8de06.png">

<br>

- 게시글 작성
<img width="430" alt="KakaoTalk_20220326_142808192" src="https://user-images.githubusercontent.com/61276416/160230006-52260030-6c2e-4d8b-818e-e892bd23cc2a.png">

<br>

## 4. 문제점 및 오류해결

<br>

### 1. 이미지 다중 업로드
 - 문제점 : 이미지를 최대 3개까지로 제한해 각각 버튼(이미지1, 이미지2, 이미지3)으로 하나씩 업로드하려했지만 번거로움의 문제 발생
 - 해결방법 : 업로드버튼을 하나만 만들고 이미지를 다중으로 선택할 수 있게한다. 선택된 이미지의 uri를 배열에 저장하고 해당 배열을 <br>
String형식으로 Oracle DB에 저장후 게시글 상세정보를 가져올때 다시 배열형식으로 만들어 for문을 통해 uri주소로 이미지를 노출시킨다.

<img width="430" alt="업로드버튼1개강조" src="https://user-images.githubusercontent.com/61276416/160230272-31cab6bf-4726-49f3-b732-000586d84f9f.png">


```
<form id="frm" action="sellbbswriteAf.do?bid=3" method="get">

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

## 5. 느낀점 및 소감
- 첫번째 프로젝트때 하지 못했던 이미지업로드 기능을 구현했다.
