
function NeatDialog(sHTML, sTitle, bCancel)
{
window.neatDialog = null;
this.elt = null;
if (document.createElement && document.getElementById)
{
    var dg = document.createElement("div");
    dg.className = "tanchukuan";
    if (sTitle)
      sHTML = '<div class="tanchukuan-title">'+sTitle+((bCancel)? '<img src="cha.gif" alt="删除" id="chapuc" class="chapuc" />':'')+ '</div>\n' + sHTML;
    dg.innerHTML = sHTML;

    var dbg = document.createElement("div");
    dbg.id = "nd-bdg";
    dbg.className = "bg_touming";

    var dgc = document.createElement("div");
    dgc.className = "dicen";
    dgc.appendChild(dbg);
    dgc.appendChild(dg);

    if (document.body.offsetLeft > 0)
      dgc.style.marginLeft = document.body.offsetLeft + "px";

    document.body.appendChild(dgc);
     this.elt = dgc;
     window.neatDialog = this;

    if (bCancel) document.getElementById("chapuc").onclick = function()
    {
      window.neatDialog.close();
    };
   
}
}
NeatDialog.prototype.close = function()
{
if (this.elt)
{
    this.elt.style.display = "none";
    this.elt.parentNode.removeChild(this.elt);
}
window.neatDialog = null;
}

function openDialog()
{

var sHTML = '<p>西西<a target="_blank" href="/" class="wordstyle">WEB开发</a>脚本特效集演示中心，<a href="">国内最大的<a target="_blank" href="/" class="wordstyle">WEB开发</a>资源社区！</p>'+
      '<p><button onclick="window.neatDialog.close()">关闭!</button></p>';
    new NeatDialog(sHTML, "欢迎光临!我叫黄玉魁", true);

}
