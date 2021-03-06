package com.example.healthapp.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.R
import com.example.healthapp.login.LoginMemberDao
import com.example.healthapp.work.WorkDto

class MypageRoutineActivity : AppCompatActivity() {

    var workList = arrayListOf<WorkDto>(
        WorkDto(1,"덤벨 숄더 프레스", "측면 삼각근을 활용하는 운동입니다", 0, 0,2131230938,"덤벨 숄더 프레스는 효과적이고 안정적으로 삼각근을 단련할 수 있는 운동으로 초보자에게도 추천하는 운동 입니다.\n" +
                "\n" +
                "덤벨 숄더 프레스 자극 부위는 보통 전면 삼각 극에 큰 타격을 줄 수 있는 운동으로 알고 있지만, 팔의 위치에 따라서 전면과 측면 모두에 자극을 줄 수 있는 장점이 있습니다.\n" +
                "\n" +
                "초보자가 덤벨 숄더 프레스를 진행하게 될 경우 오히려 머신과 바벨보다 어깨를 사용하는 것에 빨리 익숙해질 수 있지만\n" +
                "단점이라면 바벨을 사용하는 것보다 높은 중량을 다루기 힘들어서 고중량 운동으로는 적합하지 않습니다.\n" +
                "\n" +
                "운동이 적합한 사람\n" +
                "가동 범위와 관절이 자유로워 관절에 통증이 있는 사람들에게는 유리합니다..\n" +
                "초보자는 위험한 상황에서 덤벨을 던지고 벗어날 수 있으므로 부상을 방지할 수 있습니다..\n" +
                "초보자는 바벨보다 덤벨로 어깨 근육 사용법을 익히는 게 더 도움이 됩니다.\n" +
                "고중량보다는 저,중중량을 다루는 사람에게 자극 위주에 운동을 하기에 적합합니다."),
        WorkDto(2,"덤벨 스쿼드", "하체운동의 꽃! 스쿼트입니다.", 3, 0, 2131230941, "일단 덤벨스쿼트는 기본적인 스쿼트 동작에 덤벨을 이용한 중량을 더하는 동작으로 일반적인 맨몸보다는 훨씬 효율적일 수 있으나 어느정도의 운동 테크니션을 필요하기\n" +
                "때문에 충분히 숙지하면서 실시해주는게 좋습니다.\n" +
                "\n" +
                "일단 주력으로 발달시키는 근육은 앞 허벅지 쪽인 대퇴사두근과 엉덩이 올림을 도와주는 엉덩이 둔근에 자극이 많이 들어가는 편입니다. 추가로 협력근은 뒤쪽 허벅지인\n" +
                "슬 굴곡근 아랫배 하복부와 허리 쪽인 척주기립근 그리고 종아리의 가자미근과 정강이 쪽에 위치한 전경골근이 있습니다.\n" +
                "\n" +
                "상체가 앞으로 굽혀지는 경우가 있는데 내려갈 때 상체가 앞으로 숙어지게 되면 무게중심 자체가 발 앞쪽으로 가기에 무릎과 발목의 부상 위험이 높아집니다 고로 이운동에 퀵 포인트는 상체는 딱 세우도록 하는 것 입니다."),
        WorkDto(3,"덤벨 프레스", "가슴근육인 대흉근을 활용하는 운동입니다", 1, 0, 2131230939, "덤벨프레스 운동은 덤벨로 수행하는 벤치프레스 운동입니다. 초급 보다는 중급 이상의 난이도를 갖고있는 벤치프레스 운동을, 덤벨을 통해서 하게 되면 무게는 가벼워지지만 컨트롤은 더 힘들어집니다. 그 래서 초보 운동인에게는 추천드리지 않습니다. 섬세하고 다양한 자극을 원하는분들께 추천하는 운동입니다.\n" +
                "\n" +
                "덤벨프레스는 가슴의 근육을 전체적으로 발달 시켜 줍니다. 대근육 운동이고 두께를 전체적으로 두텁게 발달시킬수 있습니다. 또, 덤벨을 가지고 운동함으로써 볼 수 있는 장점이 있습니다.\n" +
                "\n" +
                "운동 기구로 덤벨을 사용 하게 되면 가동 범위를 더 넓게 가져갈수 있습니다. 그래서 가슴의 안 , 바깥, 중앙 등 다양하게 자극을 할 수 있습니다. 더 입체적이고 섬세한 근육을 발달 시키는데 효과적입니다."),
        WorkDto(4,"덤벨 로우", "광배근, 능형근, 승모근을 발달시킬 수 있는 운동입니다.", 5, 0, 2131230940, "덤벨로우는 등 근육 발달 효과를 가진 운동입니다. 덤벨 2개만 있으면 어디서든 할 수 있고 다양한 응용이 가능하여 활용성도 뛰어납니다. 물론 등 근육의 완벽한 발달을 위해서는 덤벨로우 운동만 실시하는 것은 부족할 수 있고, 데드리프트 풀업과 같은 대표운동을 하면서 덤벨로우를 같이 운동해주면 좋습니다.\n" +
                "\n" +
                "등 근육에서 광배근을 특히 자극하게 되는데, 덤벨로우는 가동 범위가 넓으므로 전체적인 등 근육 부위에 넓게 자극을 줍니다. 세심하게 발달시킬 수 있다는 것 또한 장점\n" +
                "입니다. 로우 운동은 초보자가 도전하기 좋은 운동이지만 덤벨은 가동 범위가 넓은 만큼 스스로 컨트롤 조절을 하기 까다롭고 자극 점을 찾기 힘들기도 합니다. 그래서\n" +
                "초보자는 바벨로우 등의 운동을 먼저 해보고 덤벨로우를 해보시기를 바랍니다.\n" +
                "\n" +
                "주의사항은 덤벨로우를 수행하기 전에 반드시 충분한 스트레칭을 하는 것이 중요합니다. 스트레칭은 등 근육의 긴장을 풀어주며 덤벨로우를 하는 동안 발생할 수\n" +
                "있는 부상의 위험을 최소화해줍니다. 만약 덤벨로우를 하는 동안 근육의 통증이 느껴진다면 즉시 멈추고 휴식을 취해야 합니다. 그리고 덤벨로우는 덤벨을 복부가 아닌\n" +
                "가슴 쪽으로 당기면 등 근육의 개입이 적어질 수 있습니다. 덤벨을 가슴 쪽으로 당기면 팔의 상완 이두근에 자극을 줄 수 있으므로 주의해야 합니다. 등 근육을 효과적으로\n" +
                "발달시키고 싶다면 덤벨로우 횟수가 아닌 정확한 덤벨로우 자세가 필요합니다."),
        WorkDto(5,"딥 스쿼트", "맨몸으로 할 수 있는 스쿼트 동작입니다.", 3, 0, 2131230942, "스쿼트(squat)는 직립한 상태에서 무릎 관절을 굽혔다 펴는 행동을 반복함으로써, 하반신의 대퇴사두근과 함 스트링, 대둔근, 중 전근 등의 근육을 성장시켜\n" +
                "근육량 증가시켜 주는과 효과가 좋은 운동법입니다.\n" +
                "\n" +
                "스쿼트는 대퇴사두근, 대둔근, 슬굴곡근이 단련되면서 허벅지와 엉덩이, 종아리를 포함한 하체 근육을 강화시켜주는 효과가 있습니다. 초보자도 운동 효과를 빠르게 얻을 수 있으며, 매일 스쿼트를 꾸준히 반복할 경우 호르몬 분비가 왕성해지면서 근육량 향상에도 도움이 됩니다. 척추 주변 근육이 약하면 중력에 따라 허리가 굽으면서 통증이 발생할 수 있습니다. 스쿼트로 허리 근육을 단련시키면 허리와 목에 안정성이 생기며, 가슴의 반대쪽에 있으면서 등을 펴주는 역할을 하는 하부 승모근을 발달시켜 거북목을 예방하는 등 자세 교정 효과도 있습니다.\n" +
                "\n" +
                "주의할점은 처음부터 많은 양을 하려고 욕심내지 않습니다. 매일 꾸준히 1주 차에는 4회씩 1세트, 2주 차에는 10회씩 3세트 순으로 점차 늘려가는 것이 좋습니다.\n" +
                "맨몸 스쿼트가 쉽다면 짐볼을 활용하거나, 케틀벨처럼 무게가 있는 중량 기구를 사용하면 운동효과를 더욱 높일 수 있습니다. 어떤 운동을 하던 자신에게 맞는 강도로 해야 합니다. 관절에 문제가 있는 사람은 스쿼트를 피하고, 운동 중 통증이 발생하면 즉시 멈추세요."),
        WorkDto(6,"크런치", "코어근육을 강화 할 수 있는 복근운동입니다.", 2, 0, 2131230935, "크런치(윗몸일으키기)는 누운 상태에서 양손을 머리 뒤에 깍지를 낀 상태로 다리는 고정하고 상체를 앞으로 굽혔다가 팔꿈치가 무릎을 닿고 다시 내려가는 것을 반복하는\n" +
                "운동입니다.\n" +
                "\n" +
                "윗몸 일으키기는 윗몸 일으키기를 하면 배에 힘을 주면서 몸을 일으키기 때문에 복근이 발달하고 일어나려는 힘으로 의해 허리의 힘이 키워지면서 유연성이 함께 생기게\n" +
                "됩니다. 또 윗몸 일으키기는 복근을 가장 많이 사용하는 운동이기 때문에 복근을 단련시키기 가장 효과적인 운동입니다. 복근이 생기면서 뱃살이 저절로 빠진다고 생각을\n" +
                "하기도 하는데 윗몸 일으키기만으로는 뱃살을 빼기에는 역부족이므로 단순히 근육을 강화하는 운동임에 따라 뱃살은 유산소를 병행해야지 효과를 볼 수 있습니다.\n" +
                "\n" +
                "주의할점은 만약 복부에 근육이 발달하지 않는 사람이 할 때에는 잘못하면 허리에 부담을 많이 줄 수 있습니다. 또, 평소 요통이 있던 분들이라면 윗몸일으키기를 통해 요통이 더 심해질 수도 있는데요 윗몸일으키기를 할 때 척추를 계속 앞으로 구부리게 되면 반복적으로 자극을 주어 신경이 통하는 길을 차단하고, 척추 통증을 유발할 수 있습니다."),
        WorkDto(7,"마운틴 클레이머", "탄탄한 몸매를 만들 수 있는 전신운동입니다.", 6, 0, 2131230936, "마운틴 클라이머(Mountain Climber) 말 그대로 산을 오르는 것처럼 하는 운동입니다. 무릎을 가슴 쪽으로 끌어당기면서 하는 운동입니다. 그리고 마운틴 클라이머의 가장 큰 장점은 장소나 시간의 구애를 받지 않고 또한 기합 소리를 크게 내지 않는 한 소음을 많이 유발하지도 않기 때문에 한 평 남짓한 지면에서도 어디서든지 할 수 있다는 것\n" +
                "입니다. 마운틴 클라이머는 코어 운동인 플랭크 자세에서 운동하는 운동이기 때문에 만약 플랭크에 익숙하신 분이라면 훨씬 쉽게 따라 하실 수가 있습니다.\n" +
                "\n" +
                "마운틴 글라이머는 심폐지구력을 강화해줍니다. 산을 계속 오르다 보면 심박수가 빠르게 증가하는 게 느껴 지지실 겁니다. 이처럼 마운틴 클라이머는 유산소 운동인 동시에\n" +
                "또한 여러 근육을 사용하는 근력운동이기 때문에 심박수를 빠르게 증가시킵니다. 다리 움직임의 속도를 조절하면서 오랫동안 할 수 있는 거라서 심폐지구력을 기르는 데 매우 효과적입니다. 복부가 날씬해집니다. 마운틴 클라이머는 크런치하고 같이 누워서 하는 운동이기 때문에 복부 운동하고 달리 허리에는 부담이 없습니다.\n" +
                "\n" +
                "주의하실 점은 엉덩이를 천장을 향해서 높이 들어 올려서 무릎만 시곗바늘처럼 움직이시면 안 됩니다. 그렇게 되면 전혀 복부에 자극이 되지 않고 운동 효과도 없으니 플랭크 자세를 유지한 채 복부하고 허리에 힘을 주고 운동하셔야 합니다."),
        WorkDto(8,"플랭크", "코어근육을 강화할 수 있는 복근운동입니다.", 2, 0, 2131230937, "플랭크 운동은 몸의 코어를 강화하는 운동입니다. 코어란 몸의 중심이란 의미로 등과 복부, 골반 등의 근육을 의미합니다. 각각의 근육들은 올바른 균형과 자세를 만들어주고 척추와 골반을 지탱해 주는 역할을 한답니다. 따라서, 이러한 운동 방법은 다른 운동을 할 때 부상의 위험을 낮춰주고 날씬하고 탄력 있는 복부를 만드는데 좋은 운동입니다.\n" +
                "\n" +
                "플랭크는 일반적으로 물리 치료사가 요통을 호소하는 환자들에게 추천하는 운동입니다. 왜냐하면 플랭크는 척추에 큰 무리를 주지 않고도, 코어를 강화할 수 있는 운동이기 때문입니다. 플랭크를 매일 진행하면 척추가 곧게 펴지고, 안정적인 자세가 만들어집니다. 복근을 단련함으로써 올바른 자세를 유지하게 되는데, 복부에 있는 근육이 목\n" +
                "어깨, 가슴, 허리와 같은 근육에도 영향력을 미치기 때문입니다.\n" +
                "\n" +
                "어깨뼈(날개뼈)가 등 뒤로 튀어나오면 안 되며 전거근을 이용해서 어깨뼈를 최대한 갈비뼈에 붙여야 합니다. 어깨뼈가 등 뒤로 튀어나온 상태로 버티면 힘이 적게 들어가서 편할지 모르지만, 어깨에 무리가 갑니다. 초보자의 경우는 복근의 힘이 언제 풀리는지 모르고 무리하게 버티는 경우가 있는데 이렇게 되면 허리에 무리가 갈 수 있기에 주의해야 합니다."),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage_routine)

        getSupportActionBar()!!.setIcon(R.drawable.appbar)
        getSupportActionBar()!!.setDisplayUseLogoEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        getSupportActionBar()!!.setElevation(0F)

//        val id = LoginMemberDao.user?.id
//        var data = emptyArray<Int>()
//        //val data = MypageDao.getInstance().getMyRoutine(id!!)
//        val likeList = MypageDao.getInstance().getWorkLikeCount()
//        if(likeList != null){
//            for(a in 0 until likeList!!.size){
//                var list = likeList[a].work_no
//                for(b in workList){
//                    if(list == b.workseq){
//                        data.
//                    }
//                }
//
//            }
//        }
//
//
//
//        println("!!!!!!!!!! 운동루틴목록 확인" + data)
//
//        var recycleV = findViewById<RecyclerView>(R.id.recyRoutine)
//        val adap = AdapterRoutine(this, data!!)
//        recycleV.adapter = adap
//
//        val layout = LinearLayoutManager(this)
//        recycleV.layoutManager = layout
    }
}