怎么build代码和提交代码

1.在Java Build Path 配置中把swing-beaninfo, client, substance, DJNatvieSwing, swingx-core, 这几个文件都加到Source里。

2.在Java Build Path 配置中把lib下面的所有jar 加到lib 里。

3.SVN 使用注意：
a.请不要将动态生成的目录添加到SVN，否则其他人Check out的之后，再Update容易出错，例如classes，.project,.classpath,bin,.setting目录，不需要提交上去，本地就可以了。
b.请在提交前，同步和合并代码。 只提交差异代码。