let menu_li  = document.querySelectorAll(".menu-li")

menu_li.forEach(function (elt) {
    elt.addEventListener('click',function () {

        let sub_menu = this.querySelector(".sub-menu")
        let nb = sub_menu.querySelectorAll("li").length
        
        if (sub_menu.classList.contains("h-0")) {
            hideAllMenuLi()
        }
        sub_menu.classList.toggle("h-0")
        if(nb <= 2){
            sub_menu.classList.toggle("h-20")
            sub_menu.classList.toggle("pt-3")
        }else if(nb <=3){
            sub_menu.classList.toggle("h-24")
        }
    })
})

function hideAllMenuLi() {
    menu_li.forEach(function (elt) {

        const sub_menu_in = elt.querySelector(".sub-menu")
        const nb_in = sub_menu_in.querySelectorAll("li").length

        sub_menu_in.classList.add("h-0")
        if(nb_in <= 2){
            sub_menu_in.classList.remove("h-20")
            sub_menu_in.classList.remove("pt-3")
        }else if(nb_in <=3){
            sub_menu_in.classList.remove("h-24")
        }
    })
}
