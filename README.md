## DBセットアップ

参考：Dockerで作るpostgres環境
https://crudzoo.com/blog/docker-postgres

jdbc文字列
```
jdbc:postgresql://localhost:5433/postgres?password=admin&user=admin
```

起動 
```
docker-compose up -d
```
終了
```
docker-compose down
```

Herokuのjdbc文字列取得
```bash
heroku run echo \$JDBC_DATABASE_URL -a guild-game
```
