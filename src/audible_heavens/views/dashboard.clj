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
          (view/page-header "Dashboard" "A few high-level views into the data")
          [:div.row
            [:div.col-md-6 (view/panel "Luminosity" [:div#lumgraph])]
            [:div.col-md-6 (view/panel "Color" [:div#colorgraph])]]
          [:div.row
            [:div.col-md-6 (view/panel "Abs Mag" [:div#absmapgraph])]
            [:div.col-md-6 (view/panel "App Mag" [:div#appmaggraph])]]]
        (view/common-footer)]
        [:script {:src "/static/js/vis/dist/vis.js"}]
        [:script {:src "/static/js/dashboard.js"}]]))