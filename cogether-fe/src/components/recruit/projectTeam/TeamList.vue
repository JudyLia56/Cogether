<template>
  <h2>Project</h2>
  <ul class="list-group list-group-flush">
    <!-- 글 없음 -->
    <span
      class="noData"
      v-if="tabState != 'scrap' && getters.getProjectTeams.length == 0"
    >
      등록된 글이 없습니다.
    </span>
    <!-- 스크랩 탭 -->
    <span v-if="tabState == 'scrap'">
      <span v-if="!checkScrap()"> 스크랩된 글이 없습니다.</span>
      <span v-for="(projectTeam, i) in getters.getProjectTeams" :key="i">
        <span
          v-if="
            searchText == null ||
            projectTeam.title.indexOf(searchText) != -1 ||
            projectTeam.userNickname.indexOf(searchText) != -1
          "
        >
          <span v-if="projectTeam.scrap">
            <TeamItem :projectTeam="projectTeam" @setModal="setModal" />
          </span>
        </span>
      </span>
    </span>
    <!-- 내가 쓴 글 -->
    <span v-else-if="tabState == 'my'">
      <span v-for="(projectTeam, i) in getters.getProjectTeams" :key="i">
        <span
          v-if="
            searchText == null ||
            projectTeam.title.indexOf(searchText) != -1 ||
            projectTeam.userNickname.indexOf(searchText) != -1
          "
        >
          <TeamItem :projectTeam="projectTeam" @setModal="setModal" />
        </span>
      </span>
    </span>
    <!-- 프로젝트 탭 -->
    <span v-else>
      <span v-for="(projectTeam, i) in getters.getProjectTeams" :key="i">
        <span
          v-if="
            searchText == null ||
            projectTeam.title.indexOf(searchText) != -1 ||
            projectTeam.userNickname.indexOf(searchText) != -1
          "
        >
          <TeamItem :projectTeam="projectTeam" @setModal="setModal" />
        </span>
      </span>
    </span>
  </ul>
  <!-- 모달창 -->
  <div
    v-if="emitTeam != null"
    class="modal fade"
    id="teamDetailInfo"
    tabindex="-1"
    aria-labelledby="teamDetailInfoLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header justify-content-space-evenly">
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="profile-detail-box d-flex">
            <div class="profile-img-box">
              <img
                class="profile-img"
                :src="emitTeam.userImgurl"
                alt="profile image"
              />
            </div>
            <div class="profile-detail-info">
              <div class="d-flex">
                <h5 class="modal-title" id="teamDetailInfoLabel">
                  {{ emitTeam.userNickname }}
                </h5>
              </div>
            </div>
          </div>
          <div class="team-recruit-info-box">
            <div class="d-flex flex-column">
              <div class="d-flex justify-content-start info-box">
                <h5>진행방식</h5>
                <p>{{ emitTeam.online }}</p>

                <h5>모집상태</h5>
                <p>{{ emitTeam.cur_mem }} / {{ emitTeam.total_mem }}</p>
              </div>
              <div class="d-flex justify-content-start info-box">
                <h5>시작예정일</h5>
                <p>{{ emitTeam.start }}</p>
              </div>
            </div>
          </div>
          <div class="team-detail-introduction">
            <p>
              {{ emitTeam.content }}
            </p>
          </div>
        </div>
        <div class="modal-footer justify-content-center">
          <button
            type="button"
            class="btn"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="sendDM"
          >
            <font-awesome-icon icon="fa-solid fa-comments" />
            DM 보내기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TeamItem from "@/components/recruit/projectTeam/TeamItem.vue";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import router from "@/router";
export default {
  name: "TeamList",
  props: ["searchText", "tabState"],
  setup(props) {
    const store = useStore();
    const getters = computed(() => store.getters);

    if (props.tabState == "my") {
      store.dispatch("getMyProjectTeams", getters.value.getLoginUserId);
    } else {
      store.dispatch("getProjectTeams", getters.value.getLoginUserId);
    }

    function checkScrap() {
      for (const item of getters.value.getProjectTeams) {
        if (item.scrap) return true;
      }
      return false;
    }

    // 모달창emit관련
    let emitTeam = ref("");
    function setModal(data) {
      emitTeam.value = data;
      emitTeam.value.start = data.start.substring(0, 10);
    }

    async function sendDM() {
      await store.dispatch("createChatRoom", emitTeam.value.userId);
      store.dispatch("getChatRoomList", getters.value.getLoginUserId);
      store.commit("SET_IS_CHAT_SHOW", !getters.value.getIsChatShow);
      router.push({ name: "RecruitMain" });
    }

    return { store, getters, checkScrap, emitTeam, setModal, sendDM };
  },
  components: {
    TeamItem,
  },
};
</script>

<style scoped>
.list-group {
  max-height: 500px;
  overflow: auto;
}

.noData {
  max-height: 200px;
  /* border-top: 2px solid black; */
  /* border-bottom: 2px solid black; */
  margin-bottom: 30px;
}

::-webkit-scrollbar {
  display: none;
}

/* 모달창 */
.modal-content {
  background-color: #eff7f6;
}

/* Profile Image */
.profile-img-box {
  width: 65px;
  height: 65px;
  border-radius: 70%;
  overflow: hidden;
  margin: 10px;
  text-align: center;
}

.profile-img {
  width: 100%;
  height: 100%;
}

.modal-header > .bookmark-icon {
  font-size: 25px;
  padding: 0 10px;
}

.modal-header {
  border: 0;
}

.modal-body {
  padding: 20px 80px;
  padding-top: 0px;
}

.profile-detail-info {
  padding-top: 4px;
}

.modal-title {
  margin-left: 5px;
  margin-top: 10px;
  font-size: 20px;
  font-weight: 700;
}

.date {
  font-size: 14px;
  color: gray;
}

.team-recruit-summary {
  font-size: 14px;
}

.team-recruit-info-box {
  background-color: #2a9d8f;
  border-radius: 10px;
  margin: 10px 0;
  padding: 10px;
}

.info-box > h5 {
  font-size: 14px;
  font-weight: 700;
  width: 80px;
  text-align: center;
  color: #fff;
}

.info-box > p {
  font-size: 12px;
  font-weight: 700;
  width: 100px;
  background-color: #c1ebe6;
  border-radius: 10px;
  text-align: center;
}

.team-detail-introduction {
  padding: 15px;
}

.modal-footer > .btn {
  background-color: #2a9d8f;
  color: #fff;
}

.btn {
  height: 40px;
  background-color: #2a9d8f;
  color: #fff;
  border: 0px;
  transition: all 0.2s linear;
}

.btn:hover {
  transform: scale(1.1);
}
</style>
