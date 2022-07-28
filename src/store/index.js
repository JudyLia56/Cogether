import { createStore } from "vuex";
import { challengeStore } from "@/store/modules/challengeStore.js";
import { chatStore } from "@/store/modules/chatStore.js";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    challengeStore,
    chatStore,
  },
});
