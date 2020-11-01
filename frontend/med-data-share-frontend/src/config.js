import UserRole from './constants/UserRole';

let CONFIG = {
    baseUrlAPI: 'http://localhost:PORT/api/',
    baseUrl: 'http://localhost:8085/api',
    imageUrlRegistry: 'http://localhost:8081/resource/image/name/',
    tokenKey: '1stKgorpnoj8mFIsI3sg',
    refreshTokenKey: 'ZJmD63XQFsK6cHUWwN5T',
    clientId: 'spring-security-oauth2-read-write-client',
    clientSecret: '$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W',
    facebookAppId: '352137342869506',
    facebookAppSecret: 'FACEBOOK_APP_SECRET',
    googleClientId: 'GOOGLE_CLIENT_ID',
    googleClientSecret: 'GOOGLE_CLIENT_SECRET',
    linkedInClientId: 'LINKEDIN_CLIENT_ID',
    linkedInCliendSecret: 'LINKEDIN_CLIENT_SECRET',
    rowsPerPage: [ 5, 10, 25 ],
    perPage: 2,
    pages: 3,
    rolesAllowed: [UserRole.USER, UserRole.DOCTOR, UserRole.ROLE_MED_ADMIN, UserRole.ROLE_SUPER_ADMIN],
};

export default CONFIG;