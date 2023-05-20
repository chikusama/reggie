# reggie
take out system
自学自習用で作成しました

・reggie_take_outは管理側のウェブシステム 
・reggie_take_out_frontはユーザーアプリケーションシステム

管理側サービスバックエンドはJavaで作成し、 サービスは以下になります： 
・人員管理サービス 
・料理管理サービス 
・注文確認サービス

ユーザー側アプリケーションシステムは以下になります： 
・注文サービス 
・会計サービス

フロント作成はJs、Css、Html、Vueで作成しました。

管理側の登録のパスワードを暗号化しています。
ユーザーの登録は携帯電話番号で登録し、検証コードを入力する形になります。

DB構成：MysQL、Redis

管理側トップページ
![image](https://github.com/wmz-personal/reggie/assets/64370853/ea9dc30e-b2ce-40b6-b593-f582c51d0409)

ユーザー側トップページ
![image](https://github.com/wmz-personal/reggie/assets/64370853/a623598a-68ce-4231-b865-73fb56c11ba9)

データベーステーブル構成
![image](https://github.com/wmz-personal/reggie/assets/64370853/480530cd-5fc0-44c8-a8b3-7280aa148cd8)


