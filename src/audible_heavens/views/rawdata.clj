(ns audible_heavens.views.rawdata
  (:require [hiccup.core :refer :all]
            [audible_heavens.global :as g]
            [audible_heavens.views :as v]))

(defn sort-buttons []
  [:h4 "Sort By"]
  [:div#sorts.button-group
    [:button.btn.btn-default.btn-primary {:data-sort-by "original-order"} "original order"]
    [:button.btn.btn-default {:data-sort-by "name"} "name"]
    ; [:button.btn.btn-default {:data-sort-by "coords"} "Coords"]
    [:button.btn.btn-default {:data-sort-by "distance"} "distance"]
    [:button.btn.btn-default {:data-sort-by "luminosity"} "luminosity"]
    [:button.btn.btn-default {:data-sort-by "color"} "color"]
    [:button.btn.btn-default {:data-sort-by "speed"} "speed"]
    [:button.btn.btn-default {:data-sort-by "absmag"} "abs mag"]
    [:button.btn.btn-default {:data-sort-by "appmag"} "app mag"]])

(defn star-item [value label selector]
  [:div {:data-sound value} 
    [:i.clickable.glyphicon.glyphicon-music] 
    "&nbsp;&nbsp;" 
    [:span.text-info (str label ": ")] 
    [selector value]])

(defn star-row [star]
  [:div.isotope-star-item.well
    [:h4.name.text-primary.strong (get star :label)]
    [:small.coords.text-warning (str "(" (get star :x) ", " (get star :y) ", " (get star :z) ")" )]
    [:hr]
    (star-item (get star :distly) "Distance" :span.distance.text-success)
    (star-item (get star :lum) "Luminosity" :span.luminosity.text-success)
    (star-item (get star :colorb_v) "Color" :span.color.text-success)
    (star-item (get star :speed) "Speed" :span.speed.text-success)
    (star-item (get star :absmag) "Abs Mag" :span.absmag.text-success)
    (star-item (get star :appmag) "App Mag" :span.appmag.text-success)])

(defn allstars [stars]
  (html
    [:html
      (v/common-head)
      [:body
        (v/nav-bar)
        [:div.container
          (v/breadcrumbs [["home" "/"] ["all stars" "/allstars"]])
          (v/page-header "All Stars" (str "showing data for " (count stars) " stars"))
          (sort-buttons)
          [:br]
          [:div#star-data.isotope-stars
            (map #(star-row %) (sort-by :name stars ))]]
        (v/common-footer)]]))