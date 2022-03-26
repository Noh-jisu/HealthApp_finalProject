# Final Project '나홀로GYM에'
- 멀티캠퍼스 앱개발 과정의 마지막단계 파이널 프로젝트
- 자유로운 주제로 앱&웹 개발 프로젝트
- 운동방법과 자세를 공유하고 나만의 루틴을 계획할 수 있는 앱 개발

<br>

## 목차
### 1. 담당업무
### 2. 개발환경
### 3. 구현기능 화면구성
### 4. 오류해결
### 5. 느낀점 및 소감

<br>

## 1. 담당업무
- 로그인 로그아웃기능 구현
- 판매게시판 기능 구현

<br>

## 2. 개발 환경 및 언어
```
- spring framework 5.3.13
- Mybatis
- apache tomcat 9.0
- Java
- JavaScript
- HTML
- CSS
- Mysql
```
<br>

## 3. 구현기능 화면구성

<br>

- 로그인화면     
![image](https://user-images.githubusercontent.com/61276416/160228895-56987ca2-664a-4e38-bc64-34093250dade.png)

- 판매게시판 목록
![image](https://user-images.githubusercontent.com/61276416/160228903-5fc85816-72b3-43f1-b615-dbc40db951de.png)

- 게시글 작성
![image](https://user-images.githubusercontent.com/61276416/160228915-ccd7f35c-f961-4f1b-b57e-30f2b8acd20d.png)

- 게시글 상세정보
![image](https://user-images.githubusercontent.com/61276416/160228941-e6a8d1e3-4794-4c78-9d0d-c947cf3ad4f7.png)

## 4. 오류해결

<br>

### 1. 화면이동
 - 문제점 : 양식 작성 후 글쓰기버튼을 누르면 어떤 게시판인지 구분해주는 bid가 null로 넘어가는 문제발생. 
현재 오류내용또한 bid가 null이라고 나온다.
 - 해결방법 : input태그의 type을 hidden으로 하여 name값과 value값을 지정하고 버튼 클릭시 유저의 작성양식과 함께 bid를 서버로 넘겨준다.
![image](https://user-images.githubusercontent.com/61276416/160228361-f548982a-2a4b-4401-aa9e-315a3c7bd292.png)

```
<form id="frm" action="sellbbswriteAf.do?bid=3" method="get">

<!-- input태그의 type을 hidden으로해서 값을 같이 넘겨주기 -->
	<input type="hidden" name="uid" value="<%=user.getUid()%>">
	<input type="hidden" name="bid" value="3">
	<div id="whiteOneFrame">
	<div id="mainFrame">
		<h2>게시글작성</h2>					
		<div id="contenttitleFrame">
			<div class="writeinfo">작성자</div>
			<div class="contentinfo"><input type="text" name="id"value="<%=user.getId()%>" readonly="readonly"></div>					
		</div>
				
		<div id="CtitleFrame">
			<div class="writeinfo">제목</div>
			<div class="contentinfo"><input type="text" name="title"></div>
		</div>
				
		<div id="ContentFrame">
			<div class="writeinfo">내용</div>
			<div id="Ccontentframe">
				<textarea rows="19" cols="80" name="content"></textarea>
			</div>
		</div>				
				
		<div id="bottomBtnframe">
			<div id="listBtn" style="cursor:pointer;" onclick="returnbbs()">목록으로</div>	
			<div id="replyBtn" style="cursor:pointer;" onclick="wirtebbs()">등록하기</div>								
		</div>					
	</div>
</div>
</form>
	
	
	
<script type="text/javascript">
function returnbbs(){
	location.href = "sellBoard.do?bid=3";
}
function wirtebbs() {
	$("#frm").submit();
}
</script>
```

<br>

### 2. 내정보관리
- 문제점 : 로그인하지 않고(Session에 저장된 정보가 없을때) 회원정보 아이콘 클릭시 오류발생
- 해결방법 : 같은 아이콘 및 태그에서 로그인정보가 없을때 로그인화면으로 이동하게 코드 작성(다른기능에서도 마찬가지)

![image](https://user-images.githubusercontent.com/61276416/160229212-b67cca09-7367-4992-9af1-e653714938b5.png)

```
<img src="./images/8.png" data-href="userInfo.do"
            <%if (user == null) {%>
            	"login.do"
            <%} else {%>
            	"userInfo.do"
            <%}%>
            ></span>
```

<br>

## 5. 느낀점 및 소감
- 처음 진행하는 프로젝트라서 다소 실력이 부족했지만, 프로그래밍을 공부하는 과정중에 가장 많은 성장이 있었다.
- 다소 짧은 기간과 부족한 실력으로 디자인부분이나 기능의 세밀한 Exception을 고려하지 못했다.
- 이미지업로드기능을 구현하지 못한게 너무 아쉽다.
