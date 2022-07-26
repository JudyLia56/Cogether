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
  faComments
);

createApp(App)
  .use(store)
  .use(router)
  .component("font-awesome-icon", FontAwesomeIcon)
  .mount("#app");
