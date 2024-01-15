package io.ganguo.demo.viewmodel.activity

import android.view.View
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityHtmlTextBinding
import io.ganguo.image.core.imageget.URLImageGetter
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/18
 *     desc   : TextView 加载Html图文（只适用通用文字、图片格式，复杂及还原度要求高的还是建议用WebView）
 * </pre>
 */
class HtmlTextVModel : BaseViewModel<ActivityInterface<ActivityHtmlTextBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.activity_html_text
    }

    private val htmlContent: String by lazy {
        "<p> <p>对不少人来说，写作是一种乐趣，写作是一种放松的方式，或者写作是一种习惯。但如果是要出书，那么写作就应该是一个目标。\n" +
                "\n" +
                "为了这个目标，你要确定什么衡量文字的标准，什么是好文章，什么只是普通文字？更重要的是，你要为自己确立一个方向，是虚构还是非虚构？是游记还是散文？是小说还是剧本？\n" +
                "\n" +
                "一旦确定了写作方向，就要在这方面持续地努力，专注地对这一类型的文章反复练习。只有这样集中火力朝一个方向深挖，才是最有效率也最能太高写作能力的。\n" +
                "\n" +
                "有人今天写写鸡汤文章，过两天又改写小说，写着写着又跑去写散文，这样的写法就像猴子摘玉米，一边摘一边掉，最后手里一个都没有。" +
                "</p>" +
                "<img src=\"http://qiniu-cdn.ganyouyun.com/uploads/shop/6405265413/images/2019/3/24/923ee846-ca69-42fc-8f3c-dbea657dc9df.png\">" +
                "<img src=\"http://qiniu-cdn.ganyouyun.com/uploads/shop/6405265413/images/2019/3/24/748b2602-d0e0-4092-8872-932cd3e476e0.png\">" +
                "<img src=\"http://qiniu-cdn.ganyouyun.com/uploads/shop/6405265413/images/2019/3/24/2ee13e79-4905-4651-b443-a56b6c9ee699.png\">" +
                "<img src=\"http://qiniu-cdn.ganyouyun.com/uploads/shop/6405265413/images/2019/3/24/d1a4b919-faa3-4319-a278-1ac6e7e9dbc8.png\">" +
                "<img src=\"http://qiniu-cdn.ganyouyun.com/uploads/shop/6405265413/images/2019/3/24/2b7eb501-fd98-420c-b9fe-281a19fbe9cc.png\">" +
                "</p>"
    }

    override fun onViewAttached(view: View) {
        var urlImageGetter = URLImageGetter(viewInterface.binding.tvHtml, true)
        viewInterface.binding.tvHtml.setHtml(htmlContent, urlImageGetter)
    }

}