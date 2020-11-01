function loadScript(src, id) {

    let element = window.document.getElementById(id);

    if(element) {
        return true;
    }

    let ref = window.document.getElementsByTagName("script")[0];
    let script = window.document.createElement("script");
    script.setAttribute('id', id);
    script.src = src;
    script.async = true;
    ref.parentNode.insertBefore(script, ref);

    return false;
}

export default loadScript;