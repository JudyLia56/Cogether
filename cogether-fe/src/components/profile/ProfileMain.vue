<template>
  <!-- Til Craete 버튼 -->
  <router-link :to="{ name: 'TilCreate' }">
    <button class="icon-body">
      <font-awesome-icon icon="fa-solid fa-pen-clip" class="pen-icon" />
    </button>
  </router-link>
  <!-- Profile -->
  <router-link
    v-if="isMyProfile"
    class="btn btn-success"
    sytle="opacity: 0.5"
    :to="{
      name: 'profileEdit',
      params: { userId: store.getters.getLoginUserId },
    }"
  >
    프로필 수정
  </router-link>
  <div class="profile-info-container1 d-flex flex-column align-items-center">
    <div class="profile-img-box">
      <img :src="imgUrl" alt="profile image" class="profile-img" />
    </div>
    <div class="username-box d-flex">
      <img :src="badgeUrls[0]" alt="badge img" class="badge-img" />
      <h3 class="username">{{ nickname }}</h3>
    </div>
  </div>
  <div class="exp-bar d-flex justify-content-center align-items-center">
    <p class="level">lv. {{ level }}</p>
    <div class="progress">
      <div
        class="progress-bar"
        role="progressbar"
        aria-label="Basic example"
        :style="'width: ' + percentage + '%'"
        :aria-valuenow="percentage"
        aria-valuemin="0"
        aria-valuemax="100"
      ></div>
    </div>
  </div>
  <div class="profile-info-container2 d-flex flex-column align-items-center">
    <p class="user-introduction">{{ intro }}</p>
    <div class="follow-box d-flex">
      <p class="follower">팔로워 {{ followerNum }}</p>
      <p class="follow">팔로우 {{ followingNum }}</p>
    </div>
    <div v-if="!isMyProfile" class="container mb-3">
      <div class="row d-flex justify-content-between">
        <div v-if="isFollow" class="btn btn-success col-6" @click="unfollow">
          언팔로우
        </div>
        <div v-else class="btn btn-success col-6" @click="follow">팔로우</div>
        <div class="btn btn-secondary col-6" @click="sendDM">메세지</div>
        <div class="btn btn-warning col-12 mt-3 text-white" @click="followOpen">
          팔로우/팔로잉 리스트
        </div>
      </div>
    </div>
    <div v-else class="container mb-3 d-flex justify-content-center">
      <div class="btn btn-success col-12 text-white" @click="followOpen">
        팔로우/팔로잉 리스트
      </div>
    </div>
    <ProfileFollow
      v-if="isFollowOpen"
      class="isModal"
      :userId="userId"
      @closeModal="closeModal"
      @minusFollower="minusFollower"
      @minusFollowing="minusFollowing"
    />
  </div>
  <!-- Tech Stack -->
  <div class="techstack-container">
    <h4>사용하는 기술</h4>
    <div class="d-flex flex-wrap" sytle="min-height: 30px;">
      <div
        v-for="(lang, idx) in userLangSkills"
        :key="idx"
        class="techstack-box"
      >
        <img
          :src="
            'https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/' +
            lang +
            '-original.svg'
          "
          alt="techstack image"
          class="techstack-img"
        />
      </div>
    </div>
  </div>
  <!-- Webpage Link -->
  <div class="webpage-link-container">
    <h4>개인 웹페이지 링크</h4>
    <div class="d-flex flex-wrap">
      <!-- git -->
      <div
        v-if="!!gitUrl"
        style="cursor: pointer"
        class="webpage-link-box"
        @click="goToOut(gitUrl)"
      >
        <img
          src="https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/github-original.svg"
          alt="webpage github icon"
          class="webpage-link-img"
        />
      </div>
      <div v-else class="webpage-link-box" style="opacity: 0.05">
        <img
          src="https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/github-original.svg"
          alt="webpage github icon"
          class="webpage-link-img"
        />
      </div>
      <!-- velog -->
      <div
        v-if="!!velogUrl"
        style="cursor: pointer"
        class="webpage-link-box"
        @click="goToOut(velogUrl)"
      >
        <img
          src="https://velog.velcdn.com/images/velog/profile/9aa07f66-5fcd-41f4-84f2-91d73afcec28/green%20favicon.png"
          alt="'webpage velog icon'"
          class="webpage-link-img"
        />
      </div>
      <div v-else class="webpage-link-box" style="opacity: 0.05">
        <img
          src="https://velog.velcdn.com/images/velog/profile/9aa07f66-5fcd-41f4-84f2-91d73afcec28/green%20favicon.png"
          :alt="'webpage velog icon'"
          class="webpage-link-img"
        />
      </div>
      <!-- tistory -->
      <div
        v-if="!!tistoryUrl"
        style="cursor: pointer"
        class="webpage-link-box"
        @click="goToOut(tistoryUrl)"
      >
        <img
          src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=http%3A%2F%2Fcfile27.uf.tistory.com%2Fimage%2F9931DF3D5B9542810872FD"
          alt="webpage tistory icon"
          class="webpage-link-img"
        />
      </div>
      <div v-else class="webpage-link-box" style="opacity: 0.05">
        <img
          src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=http%3A%2F%2Fcfile27.uf.tistory.com%2Fimage%2F9931DF3D5B9542810872FD"
          alt="webpage tistory icon"
          class="webpage-link-img"
        />
      </div>
      <!-- notion -->
      <div
        v-if="!!notionUrl"
        style="cursor: pointer"
        class="webpage-link-box"
        @click="goToOut(notionUrl)"
      >
        <img
          src="https://img.icons8.com/ios/500/notion.png"
          alt="webpage notion icon"
          class="webpage-link-img"
        />
      </div>
      <div v-else class="webpage-link-box" style="opacity: 0.05">
        <img
          src="https://img.icons8.com/ios/500/notion.png"
          alt="webpage notion icon"
          class="webpage-link-img"
        />
      </div>
      <!-- etc -->
      <div
        v-if="!!etcUrl"
        class="webpage-link-box"
        style="cursor: pointer"
        @click="goToOut(etcUrl)"
      >
        <img
          src="https://pic.onlinewebfonts.com/svg/img_229126.png"
          alt="webpage etc icon"
          class="webpage-link-img"
        />
      </div>
      <div v-else class="webpage-link-box" style="opacity: 0.05">
        <img
          src="https://pic.onlinewebfonts.com/svg/img_229126.png"
          alt="webpage etc icon"
          class="webpage-link-img"
        />
      </div>
    </div>
  </div>
  <!-- Search Bar -->
  <div class="searchbar">
    <input
      class="input-search"
      type="text"
      v-model="searchWord"
      @keyup.enter.prevent="onSubmit"
    />
    <button @click="onSubmit" class="btn-search">
      <font-awesome-icon
        icon="fa-solid fa-magnifying-glass"
        class="searchicon"
      />
    </button>
  </div>
  <!-- TIL -->
  <div class="h4 til-title">My TIL</div>
  <div class="til-container d-flex flex-wrap justify-content-evenly">
    <ProfileTil v-for="til in tilList" :key="til.tilId" :util="til" />
  </div>
</template>

<script>
<link
  rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
/>;
import ProfileTil from "./ProfileTil.vue";
import ProfileFollow from "./ProfileFollow.vue";

import http from "@/api/http";
import { ref, computed, onMounted, watch } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";

export default {
  name: "ProfileMain",
  components: {
    ProfileTil,
    ProfileFollow,
  },
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const route = useRoute();
    console.log("route 사용", route.params);
    const doit = ref(route.params.userId);
    console.log("route 사용", doit);
    watch(doit, (nv, ov) => {
      console.log("ov", ov);
      console.log("nv", nv);
    });

    if (self.name != "reload") {
      self.name = "reload";
      self.location.reload(true);
    } else self.name = "";
    watch(
      () => route.name,
      () => {
        console.debug(
          `MyCoolComponent - watch route.name changed to ${route.name}`
        );
        // Do something here...

        // Optionally you can set immediate: true config for the watcher to run on init
        //}, { immediate: true });
      }
    );

    // const getters = computed(() => store.getters);
    const userId = computed(() => {
      return route.params.userId;
    });
    const isLoggedIn = store.getters.isLoggedIn;

    // 사용하는 기술 가져오기
    store.dispatch("takeUserSkillSet", userId.value);
    const userLangSkills = computed(() => {
      return store.getters.getUserSkills;
    });

    // TIL 검색 창
    const searchWord = ref("");
    const onSubmit = async () => {
      if (searchWord.value) {
        const payload = {
          tilUserId: profileUser.value.id,
          keyword: searchWord.value,
        };
        console.log("payload", payload);
        await store.dispatch("searchMyTil", payload);
      } else {
        await store.dispatch("fetchMyTilList", profileUser.value.id);
      }
      tilList.value = [];
      const tempList = store.getters.getTilList.tilList;
      for (let i = 0; i < tempList.length; i++) {
        let tpa = {
          tilId: tempList[i].tilId,
          tilTitle: tempList[i].tilTitle,
          tilContent: tempList[i].tilContent,
          userId: tempList[i].userId,
          userImg: tempList[i].userImg,
          commentList: tempList[i].commentList,
          createdAt: tempList[i].createdAt,
          imgUrl: [],
          like: tempList[i].like,
          likeCnt: tempList[i].likeCnt,
          userNickname: tempList[i].userNickname,
        };
        // console.log(tempList[i].imgUrl[0]);
        for (let j = 0; j < tempList[i].imgUrl.length; j++) {
          console.log(tpa.imgUrl);
          tpa.imgUrl.push({
            tilImgId: tempList[i].imgUrl[j].tilImgId,
            tilId: tempList[i].imgUrl[j].tilId,
            imgUrl: tempList[i].imgUrl[j].imgUrl,
          });
        }
        tilList.value.push(tpa);
      }
    };

    // 내 프로필인지 아닌지 판단하고 알맞는 자료를 가져와 profile User 변수에 넣기
    const isMyProfile = computed(() => {
      return store.getters.getLoginUserId == userId.value;
    });

    let profileUser = ref({});
    const level = ref(0);
    const percentage = ref(0);
    const tilList = ref([]);
    const imgUrl = ref("");
    const nickname = ref("");
    const intro = ref("");
    const gitUrl = ref("");
    const tistoryUrl = ref("");
    const velogUrl = ref("");
    const notionUrl = ref("");
    const etcUrl = ref("");
    const isFollow = ref(false);

    const badgeUrls = [
      require("@/assets/bronze_badge.png"),
      require("@/assets/silver_badge.png"),
      require("@/assets/gold_badge.png"),
      require("@/assets/diamond_badge.png"),
      require("@/assets/ruby_badge.png"),
    ];

    onMounted(() => {
      (async () => {
        if (!isMyProfile.value) {
          await store.dispatch("fetchAnothertUser", userId.value);
          // if (store.getters.getBooleanValue) {
          //   router.back();
          // }
        } else {
          await store.dispatch(
            "fetchCurrentUser",
            store.getters.getLoginUserId
          );
        }
        profileUser = computed(() => {
          return isMyProfile.value
            ? store.getters.getCurrentUser
            : store.getters.getAnotherUser;
        });

        // 레벨과 잔여 경험치
        level.value = 1 + parseInt(profileUser.value.exp / 100);
        if (level.value > 50) level.value = 50;
        percentage.value = profileUser.value.exp % 100;

        imgUrl.value = profileUser.value.imgUrl;
        nickname.value = profileUser.value.nickname;
        intro.value = profileUser.value.intro;
        gitUrl.value = profileUser.value.gitUrl;
        tistoryUrl.value = profileUser.value.tistoryUrl;
        velogUrl.value = profileUser.value.velogUrl;
        notionUrl.value = profileUser.value.notionUrl;
        etcUrl.value = profileUser.value.etcUrl;
        console.log("프로필!", profileUser.value);
        console.log("제발", profileUser.value["gitUrl"]);

        // 페이지가 Created 될 때 list 가져옴
        await store.dispatch("fetchMyTilList", profileUser.value.id);
        const tempList = store.getters.getTilList.tilList;
        for (let i = 0; i < tempList.length; i++) {
          let tpa = {
            tilId: tempList[i].tilId,
            tilTitle: tempList[i].tilTitle,
            tilContent: tempList[i].tilContent,
            userId: tempList[i].userId,
            userImg: tempList[i].userImg,
            commentList: tempList[i].commentList,
            createdAt: tempList[i].createdAt,
            imgUrl: [],
            like: tempList[i].like,
            likeCnt: tempList[i].likeCnt,
            userNickname: tempList[i].userNickname,
          };
          // console.log(tempList[i].imgUrl[0]);
          for (let j = 0; j < tempList[i].imgUrl.length; j++) {
            console.log(tpa.imgUrl);
            tpa.imgUrl.push({
              tilImgId: tempList[i].imgUrl[j].tilImgId,
              tilId: tempList[i].imgUrl[j].tilId,
              imgUrl: tempList[i].imgUrl[j].imgUrl,
            });
          }
          tilList.value.push(tpa);
        }
        // console.log("tilList", tilList.value);

        await store.dispatch("fetchFollowerList", userId.value);
        const tempfollowerList = store.getters.getMyFollowerList;
        console.log("tempfollowerList", tempfollowerList);
        let followerList = [];
        for (let i = 0; i < tempfollowerList.length; i++) {
          followerList.push(tempfollowerList[i]);
        }
        console.log("followerList", followerList);
        for (let i = 0; i < followerList.length; i++) {
          if (followerList[i].id == store.getters.getLoginUserId) {
            isFollow.value = true;
          }
        }
        console.log("isFollow", isFollow.value);
      })();
    });

    const router = useRouter();

    watch(userId, () => {
      router.go();
    });

    // 모달 창
    const modalNum = computed(() => {
      return store.getters.getOpenTil;
    });
    const isTilOpen = computed(() => {
      return modalNum.value != -1;
    });
    const setNum = async (tilNum) => {
      const credentials = {
        tilId: tilNum,
        userId: store.getters.getLoginUserId,
      };
      await store.dispatch("fetchOpenTil", credentials);
      // await store.dispatch("fetchTil", credentials);
    };

    // 팔로우
    const follow = () => {
      store.dispatch("follow", profileUser.value.id);
      isFollow.value = true;
      followerNum.value = followerNum.value + 1;
    };
    // 언팔로우
    const unfollow = () => {
      store.dispatch("unfollow", profileUser.value.id);
      isFollow.value = false;
      followerNum.value = followerNum.value - 1;
    };

    const minusFollowing = () => {
      followingNum.value = followingNum.value - 1;
    };
    const minusFollower = () => {
      followerNum.value = followerNum.value - 1;
    };

    // 팔로우 모달창 열기/닫기
    const isFollowOpen = ref(false);
    const followOpen = () => {
      isFollowOpen.value = true;
    };
    const closeModal = () => {
      isFollowOpen.value = false;
    };

    const goToOut = (url) => {
      window.location.href = url;
    };

    async function sendDM() {
      await store.dispatch("createChatRoom", userId.value);
      store.dispatch("getChatRoomList", getters.value.getLoginUserId);
      store.commit("SET_IS_CHAT_SHOW", !getters.value.getIsChatShow);
    }

    const followingNum = ref(0);
    const followerNum = ref(0);

    (async () => {
      await http
        .get("following/lists/" + userId.value, {
          headers: store.getters.authHeader,
        })
        .then((res) => (followingNum.value = res.data.size))
        .catch((err) => console.error(err.response));
      await http
        .get("follower/lists/" + userId.value, {
          headers: store.getters.authHeader,
        })
        .then((res) => (followerNum.value = res.data.size))
        .catch((err) => console.error(err.response));
    })();

    return {
      store,
      badgeUrls,

      userId,
      isLoggedIn,
      isMyProfile,

      followingNum,
      followerNum,

      userLangSkills,

      profileUser,
      tilList,
      searchWord,
      onSubmit,

      modalNum,
      isTilOpen,
      setNum,

      isFollow,
      follow,
      unfollow,
      isFollowOpen,
      followOpen,
      closeModal,
      minusFollowing,
      minusFollower,

      level,
      percentage,
      imgUrl,
      nickname,
      intro,
      gitUrl,
      tistoryUrl,
      velogUrl,
      notionUrl,
      etcUrl,
      goToOut,
      sendDM,
    };
  },
};
</script>

<style scoped>
/* Profile */
.profile-info-container1 {
  margin-top: 40px;
}

.profile-img-box {
  width: 120px;
  height: 120px;
  border-radius: 70%;
  overflow: hidden;
  border: 5px solid gold;
}

.profile-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.badge-img {
  width: 25px;
  height: 25px;
}

.username-box {
  margin: 10px 0;
}

.username {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  margin-left: 10px;
}

.level {
  margin: 0;
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  font-size: 13px;
  margin-right: 10px;
}

.progress {
  width: 300px;
  height: 10px;
}

.progress-bar {
  background-color: #2a9d8f;
}

.profile-info-container2 {
  margin-top: 10px;
  font-weight: 700;
}

.follower {
  margin-right: 20px;
}

/* Tech Stack */
.techstack-container {
  margin: 0 0 30px 20px;
}

h4 {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  font-size: 16px;
}

.techstack-box {
  width: 30px;
  height: 30px;
  margin-right: 5px;
  margin-left: 5px;
  border-radius: 70%;
  overflow: hidden;
}

.techstack-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Webpage Link */
.webpage-link-container {
  margin: 0 0 30px 20px;
}

.webpage-link-box {
  width: 30px;
  height: 30px;
  border-radius: 70%;
  margin-right: 5px;
  margin-left: 5px;
  overflow: hidden;
}

.webpage-link-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Searh Bar */
.searchbar {
  border: 3px solid black;
  width: fit-content;
  height: 40px;
  border-radius: 10px;
  overflow: hidden;
  margin: 20px auto;
  padding-top: 3px;
}

.input-search {
  border: none;
}

.input-search:focus {
  outline: none;
}

.btn-search {
  border: none;
  box-shadow: none;
  background-color: inherit;
}

/* TIL */
.til-title {
  margin-left: 30px;
}
/* TIL Create 아이콘 */
.icon-body {
  border: 0px;
  width: 60px;
  height: 60px;
  background-color: #2a9d8f;
  color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 5px;
  right: 25vw;
  box-shadow: 0px 3px rgba(0, 0, 0, 0.3);
}

.pen-icon {
  font-size: 30px;
}

/* Modal 창 */
.isModal {
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
}
</style>
