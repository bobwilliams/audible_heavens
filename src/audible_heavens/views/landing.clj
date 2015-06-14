(ns audible_heavens.views.landing
  (:require [hiccup.core :refer :all]
            [audible_heavens.global :as global]
            [audible_heavens.views.views :as view]))

(defn index []
  (html
    [:html
      (view/common-head)
      [:body.landing
        (view/nav-bar)
        (view/forkme)
        [:div.container
          (view/jumbotron (str "Welcome to " @global/brand-name "!") "An audible exploration of the heavens")
          (view/panel "So, what is this?" 
              [:p "Are you still struggling with the scale of the universe?  Still having a hard time realizing the vastness of space and the size of celestial objects?"]
              [:p "If so, Audible Heavens gives you a new way to explore the universe."]
              [:p "By using sounds, Audible Heavens provides another and meaningful way to understand things such as how bring a star is, how fast it is moving in relational to cosmic expansion, or even it's spatial relationtional to say the Sun."])
          (view/panel "Shout Out"
              [:p "This application consumes data provide by the " 
                [:a {:href "http://star-api.herokuapp.com/"} "Star API"] ". "
                "A big shout out to the folks over at "
                [:a {:href "https://github.com/HacktheUniverse"} "Hack the Universe"] " for exposing this data."]
              [:p [:em "As a note: this project is very much in an alpha phase."]])]
        (view/common-footer)]]))