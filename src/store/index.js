import { createStore } from "vuex";
import { challengeStore } from "@/store/modules/challengeStore.js";
import { recruitStore } from "@/store/modules/recruitStore.js";
import { chatStore } from "@/store/modules/chatStore.js";
import { signStore } from "@/store/modules/signStore.js";
import { tilStore } from "@/store/modules/tilStore.js";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    challengeStore,
    chatStore,
    signStore,
    tilStore,
    recruitStore,
  },
});
