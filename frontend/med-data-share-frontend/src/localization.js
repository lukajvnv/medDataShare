import LocalizedStrings from 'react-localization';

let strings = new LocalizedStrings({
    en: {
        appName: 'MedDataShare',

        menu: {
            Home: 'Home',
            Notifications: 'Notifications',
            Tasks: 'Tasks',
            Trials: 'My Trials',
            TrialsPreview: 'Trials preview',
            Profile: 'Profile',
            AdminPortal: 'AdminPortal',
            AddMedicalExamination: 'Add Trial'
        },

        table: {
            actions: 'Actions',
            view: 'View',
            edit: 'Edit',
            delete: 'Delete',
            confirmDelete: 'Confirm delete',
            no: 'No',
            yes: 'Yes',
            search: 'Search'
        },

        header: {
            logout: 'Logout'
        },

        validation: {
            RequiredErrorMessage: 'Required',
            MinLengthErrorMessage: 'Minimal length is ',
            MaxLengthErrorMessage: 'Maximal length is ',
            EmailErrorMessage: 'Please enter valid email',
            PasswordErrorMessage: 'Password must contain at least 6 letters, one upper case, one lower case and one number.',
            UserExistsErrorMessage: 'User with this email address already exists',
            OldPasswordDidNotMatch: 'Old password did not match',
            PasswordsNotEqual: 'Passwords do not match',
            notNumber: 'Not number',
            dateFormat: 'Required date format is DD-MM-YYYY',
            dateBefore: 'Please insert date in future',
            possibleValues: 'Possible values are: %VALUES%',
        },

        notFound: {
            notFound: 'Not found!',
            dashboard: 'Dashboard'
        },

        forbidden: {
            plain: 'Forbidden!',
            forbidden: 'Forbidden! Please sign out and sign-in once again!',
            dashboard: 'Dashboard',
            unAuthorizedAccess: 'You do not have access right to the path: '
        },

        error: {
            error: 'Error!',
            dashboard: 'Dashboard'
        },

        login: {
            email: 'Email',
            password: 'Password',
            login: 'Login',
            wrongCredentials: 'Wrong Credentials',
            signUpMsg: 'Don\'t have an account? Click here to sign up',
            forgetPasswordMsg: 'Do you forget password? Click here',
            googleSign: 'Login with google',
            other: 'Other login options'
        },

        signUp: {
            firstName: 'First name',
            lastName: 'Last name',
            birthday: 'Birthday',
            email: 'Email',
            password: 'Password',
            signUp: 'Sign Up',
            wrongCredentials: 'Wrong Credentials',
            gender: {
                female: 'Female',
                male: 'Male',
                other: 'Other'
            },
            address: 'Address'
        },

        userList: {
            firstName: 'First Name',
            lastName: 'Last Name',
            email: 'Email',
            isDeleted: 'Is deleted',
            dateCreated: 'Date Created',
            pageTitle: 'Users',
            enabled: 'Enabled',
            userDelete: 'User deleted',
            userRestored: 'User restored'
        },

        userForm: {
            email: 'Email',
            firstName: 'First name',
            lastName: 'Last name',
            ok: 'Ok',
            cancel: 'Cancel'
        },

        addUser: {
            pageTitle: 'Add user',
            clubAdded: 'User added',
            errorAddingUser: 'Error adding user',
            userAdded: 'User added'
        },

        medInstitutionList: {
            name: 'Name',
            address: 'Address',
            pageTitle: 'Medical institutions'
        },

        addMedInstitution: {
            pageTitle: 'Add med institution',
            errorAddingMedInstitution: 'Error adding med institutions',
            medInstitutionAdded: 'Med institutions added'
        },

        medInstitutionForm: {
            name: 'Name',
            address: 'Address'
        },

        medAdminList: {
            firstName: 'First Name',
            lastName: 'Last Name',
            email: 'Email',
            role: 'Role',
            isDeleted: 'Is deleted',
            dateCreated: 'Date Created',
            pageTitle: 'Medical institution\'s admins',
            enabled: 'Enabled',
            userDelete: 'User deleted',
            userRestored: 'User restored'
        },

        addMedAdmin: {
            pageTitle: 'Add med\'s admin',
            errorAddingMedAdmin: 'Error adding med institutions\'s admin',
            medAdminAdd: 'Med institutions\'s admin added'
        },

        medAdminForm: {
            email: 'Email',
            firstName: 'First name',
            lastName: 'Last name',
            password: 'Password',
            ok: 'Ok',
            cancel: 'Cancel',
            medInstitution: 'Med Institution'
        },

        doctorList: {
            firstName: 'First Name',
            lastName: 'Last Name',
            email: 'Email',
            role: 'Role',
            occupation: 'Occupation',
            isDeleted: 'Is deleted',
            dateCreated: 'Date Created',
            pageTitle: 'Medical institution\'s doctors',
            enabled: 'Enabled',
            doctorDeleted: 'User deleted',
            doctorRestored: 'User restored'
        },

        addDoctor: {
            pageTitle: 'Add doctor',
            errorAddingDoctor: 'Error adding doctor',
            doctorAdd: 'Doctor added'
        },

        doctorForm: {
            email: 'Email',
            firstName: 'First name',
            lastName: 'Last name',
            password: 'Password',
            occupation: 'Occupation',
            ok: 'Ok',
            cancel: 'Cancel',
            medInstitution: 'Med Institution'
        },

        editUser: {
            pageTitle: 'Edit user',
            passwordDoNotMatch: 'Password do not match'
        },

        clinicalTrial: {
            term: 'Clinical trial',
            detail: {
                text: 'Detail',
                pageTitle: 'Clinical trial',
                intro: 'Clinical trial',
                close: 'Close',
                ok: 'Ok',
                introduction: 'Introduction',
                relevantParameters: 'Relevant parameters',
                conclusion: 'Conclusion',
                patient: 'Patient',
                doctor: 'Doctor',
                time: 'Date',
                id: 'Clinical Trial ID',
                clinicalTrialType: 'Trial type',
                accessType: 'Access type',
                accessTypePlaceholder: 'Define access type',
                image: 'Image',
                imagePlaceholder: 'Click arrow on right to view image',
                pdf: 'Pdf file',
                institution: 'Institution',
                clinicalTrial: 'Clinical trial',
                export: "Export in pdf"
            },
            form: {
                pageTitle: 'Add clinical trial',
                intro: 'Clinical trial',
                close: 'Close',
                submit: 'Submit',
                introduction: 'Introdution',
                relevantParameters: 'Relevant parameters',
                conclusion: 'Conclusion',
                patient: 'Patient',
                patientPlaceholder: 'Type a patient', 
                doctor: 'Doctor',
                time: 'Date',
                id: 'Clinical Trial ID',
                clinicalTrialType: 'Clinical Trial type',
                cbc: 'Complete blood count',
                ct: 'CT',
                rtg: 'RTG',
                us: 'Ultrasound',
                image: 'Image',
                pdf: 'Pdf file',
                error: 'Error while adding clinical trial',
                success: 'Clinical trial added',
                file: 'Upload image or chart',
                filePlaceholder: 'Click button Upload image or chart to upload medical image',
                fileUploadError: 'Invalid format! JPG AND PNG are supported formats!',
                accessType: {
                    FORBIDDEN: 'For private use only',
                    UNCONDITIONAL: 'Unlimited access',
                    ASK_FOR_ACCESS: 'Ask for access'
                }
            },
            patientDialog: {
                pageTitle: 'Find a patient',
                desc: 'Type name to find patient',
                cancel: 'Cancel',
                submit: 'Add'
            },
            pageTitle: 'Patient\'s clinical trials',
            preview: {
                pageTitle: 'Patient\'s clinical trials preview',
                form: {
                    search: 'Search',
                    clinicalTrialType: 'Clinical trial type',
                    relevantParameters: 'Trials with relevant parameters',
                    from: 'From',
                    until: 'Until',
                    institutions: 'Institutions',
                    orderBy: 'Order by',
                    latest: 'Order by latest',
                    oldest: 'Order by oldest',
                    cancel: 'Cancel',
                    refresh: 'Refresh'
                },
                sendRequestSuccess: 'Your trial access request is successfully sent. To follow status of your access open TASKS under REQUESTED tab',
                sendRequestError: 'Error while sending trial access request'
            },
            accessType: {
                IDLE: 'IDLE',
                FORBIDDEN: 'FORBIDDEN',
                UNCONDITIONAL: 'UNCONDITIONAL',
                ASK_FOR_ACCESS: 'ASK_FOR_ACCESSs'
            }
        },
        tasklist: {
            tabs: {
                trialDefineAccess: 'Trial define access',
                trialAccessRequest: 'Trial access requests',
                trialAccessHistory: 'Trial access requests history',
                requestedTrialAccess: 'Requested trial access'
            },
            trialDefineAccess: {
                action: 'Define',
                pageTitle: 'Your trials need to be defined access type',
                submit: 'Define',
                defineAccessSuccess: 'Your action is successfully recorded',
                defineAccessError: 'Error while defining access for the trial'
            },
            trialAccessRequest: {
                term: 'Trial access request',
                date: 'Request date',
                pageTitle: 'Access requests to your clinical trial',
                detail: 'Detail',
                back: 'Back',
                decide: 'Decide',
                get: 'Get',
                noMatch: 'No data provided',
                dateCreated: 'Date created: ',
                clinicalTrialType: 'Clinical trial type: ',
                requestInformation: 'Request information',
                pageTitleSingular: 'Clinical trial access request',
                form: {
                    pageTitle: 'Clinical trial access decision',
                    decision: 'Decision',
                    anonymity: 'Ensure anonymity of patient\'s sensitive data',
                    from: 'From',
                    until: 'Until',
                    decisionSuccess: 'Your decision is successfully recorded',
                    decisionError: 'Error while recording decision',
                    forbidden: 'Disallow access to the trial',
                    unconditional: 'Allow access to the trial'
                },
                sendRequest: 'Send request',
                view: {
                    id: 'Id',
                    time: 'Request time',
                    sender: 'Sender (Id)',
                    receiver: 'Pacient',
                    clinicalTrialType: 'Clinical trial type',
                    clinicalTrial: 'Clinical trial',
                    from: 'Available from',
                    until: 'Available until',   
                    accessDecision: 'Decision',     
                    anonymity: 'Anonymity of personal data', 
                }
            },
            trialAccessHistory: {
                pageTitle: 'Access request\'s history for your clinical trials',
            },
            requestedTrialAccess: {
                pageTitle: 'Requested clinical trial\'s access by me for other patient\'s trials',
            }
        },

        resetPassword: {
            email: 'Email',
            resetPassword: 'Reset password',
            password: 'Password',
            passwordRepeat: 'Password repeat',
            cancel: 'Cancel'
        },

        profile: {
            pageTitle: 'User profile',
            information: 'User Info',
            profile: 'Profile',
            editSuccess: 'User data edited',
            editError: 'Error while editing userData',
            resetPassword: 'Reset password',
            basicInfoTitle: 'Basic information',
            edit: 'Edit',
            other: 'Other',
            form: {
                submit: 'Edit',
                imageError: 'Wrong image format(.jpg or .png are allowed)'
            },
            detail: {
                email: 'Email',
                firstName: 'First name',
                lastName: 'Last name',
                address: 'Address',
                enabled: 'Account enabled',
                gender: 'Gender',
                birthday: 'Birthday',
                activeSince: 'Active since',
                role: 'Role',
                occupation: 'Occupation',
                institution: 'Medical institution',
                id: 'Id',
                genders: {
                    Female: 'Female',
                    Male: 'Male',
                    Other: 'Other'
                },
                roles: {
                    ROLE_COMMON_USER: 'ROLE_COMMON_USER',
                    ROLE_DOCTOR: 'DOCTOR',
                    ROLE_MED_ADMIN: 'ROLE_MED_ADMIN',
                    ROLE_SUPER_ADMIN: 'ROLE_SUPER_ADMIN'
                }
            }
        }, 
        pdf: {
            page: 'Page',
            next: 'Next',
            previous: 'Previous'
        }
    }, 

    rs: {
        appName: 'MedDataShare aplikacija',
        menu: {
            Home: 'Početna',
            Notifications: 'Obaveštenja',
            Tasks: 'Zadaci',
            Trials: 'Moji pregledi',
            TrialsPreview: 'Pretraga pregleda drugih lica',
            Profile: 'Profil',
            AdminPortal: 'Portal administratora',
            AddMedicalExamination: 'Dodaj pregled'
        },

        table: {
            actions: 'Operacije',
            view: 'Prikaz',
            edit: 'Izmeni',
            delete: 'Obriši',
            confirmDelete: 'Potvrda brisanja',
            no: 'Ne',
            yes: 'Da',
            search: 'Pretraga'
        },

        header: {
            logout: 'Odjava'
        },

        validation: {
            RequiredErrorMessage: 'Obavezno',
            MinLengthErrorMessage: 'Minimalna dužina je ',
            MaxLengthErrorMessage: 'Maksimalna dužina je ',
            EmailErrorMessage: 'Molimo unesite validan email',
            PasswordErrorMessage: 'Lozinka mora sadržati najmanje 6 karaktera, jedno veliko slovo, jedno malo i jednu cifru.',
            UserExistsErrorMessage: 'Korisnik sa ovim emailom već postoji u sistemu',
            OldPasswordDidNotMatch: 'Stara lozinka se ne podudara',
            PasswordsNotEqual: 'Lozinke se ne poklapaju',
            notNumber: 'Nije broj',
            dateFormat: 'Validan format datume je: DD-MM-YYYY',
            dateBefore: 'Molimo unesite datum iz budućnosti',
            possibleValues: 'Moguće vrednosti su: %VALUES%',
        },

        notFound: {
            notFound: 'Ne postoji!',
            dashboard: 'Početna'
        },

        forbidden: {
            plain: 'Onemogućeno!',
            forbidden: 'Onemogućeno! Molim Vas odjavite se i pokušajte ponovo!',
            dashboard: 'Početna',
            unAuthorizedAccess: 'Nemate prava da pristupite putanji: '

        },

        error: {
            error: 'Greška!',
            dashboard: 'Početna'
        },

        login: {
            email: 'Email',
            password: 'Lozinka',
            login: 'Prijava',
            wrongCredentials: 'Pogrešni kredencijali',
            signUpMsg: 'Nemate nalog? Kliknite ovde da se registrujete',
            forgetPasswordMsg: 'Zaboravili ste lozinku? Kliknite ovde',
            googleSign: 'Prijava pomoću Googla',
            other: 'Druge opcije prijavljivanja'
        },

        signUp: {
            firstName: 'Ime',
            lastName: 'Prezime',
            birthday: 'Datum rođenja',
            email: 'Email',
            password: 'Lozinka',
            signUp: 'Registracija',
            wrongCredentials: 'Pogrešni kredencijali',
            gender: {
                female: 'Ženski',
                male: 'Muški',
                other: 'Neutralno'
            },
            address: 'Adresa'
        },

        userList: {
            firstName: 'Ime',
            lastName: 'Prezime',
            email: 'Email',
            isDeleted: 'Obrisano',
            dateCreated: 'Datum kreiranja',
            pageTitle: 'Korisnici',
            enabled: 'Omogućeno',
            userDelete: 'Korisnik obrisan',
            userRestored: 'Korisnik obnovljen'
        },

        userForm: {
            email: 'Email',
            firstName: 'Ime',
            lastName: 'Prezime',
            ok: 'Ok',
            cancel: 'Prekini'
        },

        addUser: {
            pageTitle: 'Dodaj korisnika',
            clubAdded: 'Korisnik je dodat',
            errorAddingUser: 'Greška prilikom dodavanja korisnika',
            userAdded: 'Korisnike je dodat'
        },

        medInstitutionList: {
            name: 'Ime',
            address: 'Adresa',
            pageTitle: 'Medicinska ustanova'
        },

        addMedInstitution: {
            pageTitle: 'Dodaj medicinsku ustanovu',
            errorAddingMedInstitution: 'Greška prilikom dodavanja medicinske ustanove',
            medInstitutionAdded: 'Uspešno dodata medicinska ustanova'
        },

        medInstitutionForm: {
            name: 'Ime',
            address: 'Adresa'
        },

        medAdminList: {
            firstName: 'Ime',
            lastName: 'Prezime',
            email: 'Email',
            role: 'Uloga',
            isDeleted: 'Obrisano',
            dateCreated: 'Datum kreiranja',
            pageTitle: 'Administratori medicinskih ustanova',
            enabled: 'Omogućeno',
            userDelete: 'Korisnik obrisan',
            userRestored: 'Korisnik obnovljen'
        },

        addMedAdmin: {
            pageTitle: 'Dodavanje administratora medicinske ustanove',
            errorAddingMedAdmin: 'Greška prilikom dodavanja administratora medicinske ustanove',
            medAdminAdd: 'Uspešno dodat administrator medicinske ustanove'
        },

        medAdminForm: {
            email: 'Email',
            firstName: 'Ime',
            lastName: 'Prezime',
            password: 'Lozinka',
            ok: 'Ok',
            cancel: 'Prekini',
            medInstitution: 'Medicinska ustanova'
        },

        doctorList: {
            firstName: 'Ime',
            lastName: 'Prezime',
            email: 'Email',
            role: 'Uloga',
            occupation: 'Stručnost',
            isDeleted: 'Obrisan',
            dateCreated: 'Datum kreiranja',
            pageTitle: 'Stručni kadar medicinske ustanove',
            enabled: 'Omogućeno',
            doctorDeleted: 'Korisnik obrisan',
            doctorRestored: 'Korisnik obnovljen'
        },

        addDoctor: {
            pageTitle: 'Dodavanje medicinskog osoblja',
            errorAddingDoctor: 'Greška prilikom dodovanje medicinskog osoblja',
            doctorAdd: 'Uspešno dodavanje medicinskog osoblja'
        },

        doctorForm: {
            email: 'Email',
            firstName: 'Ime',
            lastName: 'Prezime',
            password: 'Lozinka',
            occupation: 'Stručnost',
            ok: 'Ok',
            cancel: 'Prekini',
            medInstitution: 'Medicinska ustanova'
        },

        editUser: {
            pageTitle: 'Izmena korisnika',
            passwordDoNotMatch: 'Lozinka se ne podudara'
        },

        clinicalTrial: {
            term: 'Medicinski pregled',
            detail: {
                text: 'Detalji',
                pageTitle: 'Medicinski pregled',
                intro: 'Medicinski pregled',
                close: 'Zatvori',
                ok: 'Ok',
                introduction: 'Mišljenje',
                relevantParameters: 'Parametri',
                conclusion: 'Zaključak',
                patient: 'Pacijent',
                doctor: 'Doktor',
                time: 'Datum pregleda',
                id: 'ID pregleda',
                clinicalTrialType: 'Vrsta pregleda',
                accessType: 'Način pristupa',
                accessTypePlaceholder: 'Definišite način pristupa',
                image: 'Slika',
                imagePlaceholder: 'Kliknite strelicu desno za prikaz slike',
                pdf: 'Pdf fajla',
                institution: 'Medicinska ustanova',
                clinicalTrial: 'Medicinski pregled',
                export: 'Izvezi u pdf'
            },
            form: {
                pageTitle: 'Dodavanje pregleda',
                intro: 'Pregled',
                close: 'Zatvori',
                submit: 'Pošalji',
                introduction: 'Mišljenje',
                relevantParameters: 'Parametri',
                conclusion: 'Zaključak',
                patient: 'Pacijent',
                patientPlaceholder: 'Pretražite pacijenta', 
                doctor: 'Doktor',
                time: 'Datum',
                id: 'ID medicinskog pregleda',
                clinicalTrialType: 'Tip pregleda',
                cbc: 'Krvna slika',
                ct: 'CT',
                rtg: 'Rendgen',
                us: 'Ultrazvuk',
                image: 'Slika',
                pdf: 'Pdf fajl',
                error: 'Greška prilikom dodavanja pregleda',
                success: 'Pregled je dodat',
                file: 'Učitaj sliku',
                filePlaceholder: 'Klikni Učitaj sliku kako biste učitali medicinsku sliku ili grafik pregleda.',
                fileUploadError: 'Nevalidan format! Podržani formati su JPG i PNG!',
                accessType: {
                    FORBIDDEN: 'Samo za ličnu upotrebu',
                    UNCONDITIONAL: 'Bezuslovan pristup',
                    ASK_FOR_ACCESS: 'Prethodno zatražiti dozvolu'
                }
            },
            patientDialog: {
                pageTitle: 'Pronađite pacijenta',
                desc: 'Unesite ime kako bi pronašli pacijenta *',
                cancel: 'Otkaži',
                submit: 'Dodaj'
            },
            pageTitle: 'Pregledi pacijenta',
            preview: {
                pageTitle: 'Prikazi osnovnih informacija pregleda pacijenta',
                form: {
                    search: 'Pretraga',
                    clinicalTrialType: 'Vrsta pregleda',
                    relevantParameters: 'Pregled sa referentnim parametrima',
                    from: 'Od',
                    until: 'Do',
                    institutions: 'Medicinska ustanova',
                    orderBy: 'Sortiraj po',
                    latest: 'Sortiraj prvo novije preglede',
                    oldest: 'Sortiraj prvo starije preglede',
                    cancel: 'Poništi',
                    refresh: 'Osveži'
                },
                sendRequestSuccess: 'Uspešno poslat zahtev za pristup pregledu. Kako biste pratili status Vašeg zahteva otvoriti Zadaci, tab: Poslati zahtevi za pristup',
                sendRequestError: 'Greška prilikom zahteva za pristup pregledu',
                date: 'Datum pregleda',
                clinicalTrialType: 'Vrsta pregleda'
            },
            accessType: {
                IDLE: 'Čeka se definisanje pristupa',
                FORBIDDEN: 'Zabranjen pristup',
                UNCONDITIONAL: 'Bezuslovan pristup',
                ASK_FOR_ACCESS: 'Prethodno zatražiti dozvolu'
            }
        },

        tasklist: {
            tabs: {
                trialDefineAccess: 'Definisanje pristupa',
                trialAccessRequest: 'Aktuelni zahtevi za pristup pregledima',
                trialAccessHistory: 'Istorija zahteva za pristup pregledima',
                requestedTrialAccess: 'Poslati zahtevi za pristup'
            },
            trialDefineAccess: {
                action: 'Definiši',
                pageTitle: 'Vaši pregledi za koje je neophodno defisati pristup',
                submit: 'Definiši',
                defineAccessSuccess: 'Uspešno defisano pravo pristupa Vašem pregledu',
                defineAccessError: 'Greška prilikom definisanja prava pristupa Vašeg pregleda'
            },
            trialAccessRequest: {
                term: 'Zahtev za pristup pregledu',
                date: 'Datum zahteva',
                pageTitle: 'Zahtevi za pristup Vašim pregledima',
                detail: 'Detalji',
                back: 'Nazad',
                decide: 'Odlučite',
                noMatch: 'Nema podataka',
                get: 'Prikaži',
                dateCreated: 'Datum kreiranja',
                clinicalTrialType: 'Vrsta pregleda: ',
                requestInformation: 'Informacija o zahtevu',
                pageTitleSingular: 'Zahtev za pristup pregledu',
                form: {
                    pageTitle: 'Odluka povodom zahteva',
                    decision: 'Donesi odluku',
                    anonymity: 'Obezbedi anonimnost ličnih osetljivih podataka',
                    from: 'Od',
                    until: 'Do',
                    decisionSuccess: 'Vaša odluka je uspešno sačuvana',
                    decisionError: 'Greška prilikom čuvanja vaše odluke',
                    forbidden: 'Onemogući pristup pregledu',
                    unconditional: 'Dozvoli pristup pregledu'
                },
                sendRequest: 'Pošalji zahtev',
                view: {
                    id: 'Id zahteva',
                    time: 'Vreme zahteva',
                    sender: 'Pošiljalac (Id)',
                    receiver: 'Pacijent',
                    clinicalTrialType: 'Vrsta pregleda',
                    clinicalTrial:'Pregled',
                    from: 'Dostupno od',
                    until: 'Dostupno do',   
                    accessDecision:'Odluka',     
                    anonymity: 'Anonimnost ličnih podataka', 
                }
            },
            trialAccessHistory: {
                pageTitle: 'Istorija zahteva za pristup medicinskim pregledima',
            },
            requestedTrialAccess: {
                pageTitle: 'Vaši poslati zahtevi za pristup medicinskim pregledima',
            }
        },

        resetPassword: {
            email: 'Email',
            resetPassword: 'Resetujte lozinku',
            password: 'Lozinka',
            passwordRepeat: 'Ponovite lozinku',
            cancel: 'Odustani'
        },

        profile: {
            pageTitle: 'Profil korisnika',
            information: 'Informacije o korisniku',
            profile: 'Profil',
            editSuccess: 'Izmenjeni podaci korisniak',
            editError: 'Greška prilikom izmene podataka korisnika',
            resetPassword: 'Resetuj lozinku',
            basicInfoTitle: 'Osnovne informacije',
            edit: 'Uredi',
            other: 'Ostalo',
            form: {
                submit: 'Uredi',
                imageError: 'Pogrešan format(.jpg or .png su dozvoljeni)'
            },
            detail: {
                email: 'Email',
                firstName: 'Ime',
                lastName: 'Prezime',
                address: 'Adresa stanovanja',
                enabled: 'Nalog aktiviran',
                gender: 'Pol',
                birthday: 'Datum rođenja',
                activeSince: 'Korisnik aktivan od',
                role: 'Uloga',
                occupation: 'Specijalnost',
                institution: 'Medicinska ustanova',
                id: 'Id',
                genders: {
                    Female: 'Ženski',
                    Male: 'Muški',
                    Other: 'Neutralno'
                },
                roles: {
                    ROLE_COMMON_USER: 'Registrovani korisnik',
                    ROLE_DOCTOR: 'Medicinsko osoblje',
                    ROLE_MED_ADMIN: 'Administrator medicinske ustanove',
                    ROLE_SUPER_ADMIN: 'Administrator sistema'
                }
            }
        },

        pdf: {
            page: 'Strana',
            next: 'Sledeća',
            previous: 'Prethodna'
        }
    }
    
});

strings.setLanguage('rs');
export default strings;