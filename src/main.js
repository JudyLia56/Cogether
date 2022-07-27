import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faTowerBroadcast,
  faBell,
  faHouse,
  faGamepad,
  faPeopleRoof,
  faTrophy,
  faClipboardQuestion,
  faUser,
  faComments,
  faMagnifyingGlass,
  faHandshake,
  faPenClip,
  faPlus,
  faEllipsisVertical,
  faPeopleGroup,
  faXmark,
  faCompress,
  faExpand,
  faPaperPlane,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(
  faTowerBroadcast,
  faBell,
  faHouse,
  faGamepad,
  faPeopleRoof,
  faTrophy,
  faClipboardQuestion,
  faUser,
  faComments,
  faMagnifyingGlass,
  faHandshake,
  faPenClip,
  faPlus,
  faEllipsisVertical,
  faPeopleGroup,
  faXmark,
  faCompress,
  faExpand,
  faPaperPlane
);

createApp(App)
  .use(store)
  .use(router)
  .component("font-awesome-icon", FontAwesomeIcon)
  .mount("#app");
