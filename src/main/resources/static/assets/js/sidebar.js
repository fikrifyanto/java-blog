function bindSidebar() {
    const sidebar = document.getElementById('sidebar')
    const container = document.getElementById('container')

    if (sidebar) {
        /** Mobile **/
        if (sidebar.classList.contains('-translate-x-full')) {
            // Open Sidebar
            sidebar.classList.remove('-translate-x-full')
            sidebar.classList.add('translate-x-0')
            localStorage.setItem('sidebarOpen', 'true');
        } else {
            // Close Sidebar
            sidebar.classList.add('-translate-x-full')
            sidebar.classList.remove('translate-x-0')
            localStorage.setItem('sidebarOpen', 'false');
        }

        /** Desktop **/
        if(sidebar.classList.contains('lg:translate-x-0')) {
            // Open Sidebar
            sidebar.classList.add('lg:-translate-x-full')
            sidebar.classList.remove('lg:translate-x-0')
            container.classList.add('lg:-ml-[25%]')
            localStorage.setItem('sidebarOpen', 'true');
        } else {
            // Close Sidebar
            sidebar.classList.remove('lg:-translate-x-full')
            sidebar.classList.add('lg:translate-x-0')
            container.classList.remove('lg:-ml-[25%]')
            localStorage.setItem('sidebarOpen', 'false');
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const sidebarOpen = localStorage.getItem('sidebarOpen');
    if (sidebarOpen === 'true') bindSidebar();
});

const openSidebarButton = document.getElementById('open-sidebar-button')
if (openSidebarButton) {
    openSidebarButton.addEventListener('click', () => {
        bindSidebar()
    })
}

const closeSidebarButton = document.getElementById('close-sidebar-button')
if (closeSidebarButton) {
    closeSidebarButton.addEventListener('click', () => {
        bindSidebar()
    })
}
