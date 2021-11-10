const header = document.querySelector('#header');
const variableWidth = document.querySelectorAll('.contents_box .contents');

function resizeFunc() {
    // console.log('resize!!');

    if (matchMedia('screen and (max-width : 800px)').matches) {

        for (let i = 0; i < variableWidth.length; i++) {
            variableWidth[i].style.width = window.innerWidth - 20 + 'px';
        }

    } else {

        for (let i = 0; i < variableWidth.length; i++) {

            if (window.innerWidth > 600) {
                variableWidth[i].removeAttribute('style');
            }

        }

    }

}

function scrollFunc() {

    var scrollHeight = pageYOffset + window.innerHeight;
    var documentHeight = document.body.scrollHeight;


    // console.log(pageYOffset);

    if (pageYOffset >= 10) {
        header.classList.add('on');
        resizeFunc();


    } else {
        header.classList.remove('on');

    }

    console.log('scrollHeight : '+scrollHeight);
    console.log('documentHeight : ' +documentHeight);

    if (scrollHeight >= documentHeight) {

        var page = document.querySelector('#page').value;

        // page = parseInt(page) + 1;
        // page = parseInt(page) + 1;
        document.querySelector('#page').value = parseInt(page) + 1;
        // $('#page').val(parseInt(page) + 1);

        callMorePostAjax(page);

        if(page > 10){
            return;
        }

    }

}

setTimeout(function () {
    scrollTo(0, 0)
}, 100);



window.addEventListener('resize', resizeFunc);
window.addEventListener('scroll', scrollFunc);