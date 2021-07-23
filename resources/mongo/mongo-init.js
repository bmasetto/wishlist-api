db.createUser(
  {
    user: 'wishlist_api',
    pwd: '12345',
    roles: [{ role: 'readWrite', db: 'magalu' }],
  },
);
db.createCollection('customers');