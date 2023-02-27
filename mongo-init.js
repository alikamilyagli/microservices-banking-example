print("Started Adding the Users.");
db.createUser(
        {
            user: "historydbuser",
            pwd: "historydbpw",
            roles: [
                {
                    role: "readWrite",
                    db: "historydb"
                }
            ]
        }
);
print("End Adding the User Roles.");
