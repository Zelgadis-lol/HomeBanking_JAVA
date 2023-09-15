Vue.createApp({
    data() {
        return {
            clientInfo: {},
            creditCards: [],
            debitCards: [],
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        deleteCard: (id) => {
                Swal.fire({
                    title: 'Delete Card',
                    text: "Do you want delete the card?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'Delete'
                  }).then((result) => {
                    if (result.isConfirmed) {
                      Swal.fire('Deleted!', 'Your card has been deleted.',)
                      setTimeout(() => {
                        axios.patch(`/api/clients/current/cards/${id}`)
                            .then(res => {
                                window.location.href = "/web/cards.html";
                            })
                      }, 1000);
                    }
                })
            },
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientInfo = response.data;
                    this.creditCards = this.clientInfo.cards.filter(card => card.type == "CREDIT").filter(card => card.isActive == true);
                    this.debitCards = this.clientInfo.cards.filter(card => card.type == "DEBIT").filter(card => card.isActive == true);
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app')