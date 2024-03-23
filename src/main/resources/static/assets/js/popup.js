document.addEventListener('DOMContentLoaded', function() {
    const triggerButton = document.getElementById('popup-trigger');
    const popupContent = document.getElementById('popup-content');

    function togglePopup() {
        const expanded = triggerButton.getAttribute('aria-expanded') === 'true' || false;
        triggerButton.setAttribute('aria-expanded', !expanded);
        popupContent.classList.toggle('hidden');
    }

    triggerButton.addEventListener('click', function(event) {
        event.stopPropagation();
        togglePopup();
    });

    document.addEventListener('click', function(event) {
        if (!popupContent.contains(event.target) && !triggerButton.contains(event.target)) {
            popupContent.classList.add('hidden');
            triggerButton.setAttribute('aria-expanded', 'false');
        }
    });
});