(ns audible_heavens.views.dashboard
  (:require [hiccup.core :refer :all]
            [audible_heavens.global :as global]
            [audible_heavens.views.views :as view]))

(defn index []
  (html
    [:html
      (view/common-head)
      [:body
        (view/nav-bar)
        [:div.container
          ; (view/breadcrumbs [["home" "/"] ["dashboard" "/dashboard"]])
          (view/page-header "Dashboard" (str "graphing luminosity for the stars"))
          [:div#mygraph]
        (view/common-footer)]]]))