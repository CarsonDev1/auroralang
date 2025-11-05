// # Toast/alert notifications
(function () {
	function ensureContainer() {
		let container = document.getElementById('toast-container');
		if (!container) {
			container = document.createElement('div');
			container.id = 'toast-container';
			container.className = 'toast-container position-fixed top-0 end-0 p-3';
			document.body.appendChild(container);
		}
		return container;
	}

	function showToast(message, variant, title) {
		const container = ensureContainer();
		const wrapper = document.createElement('div');
		wrapper.className = 'toast align-items-center text-bg-' + (variant || 'info') + ' border-0';
		wrapper.setAttribute('role', 'alert');
		wrapper.setAttribute('aria-live', 'assertive');
		wrapper.setAttribute('aria-atomic', 'true');
		wrapper.innerHTML =
			'<div class="d-flex">' +
			'<div class="toast-body">' +
			(title ? '<strong class="me-2">' + title + '</strong>' : '') +
			(message || '') +
			'</div>' +
			'<button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>' +
			'</div>';
		container.appendChild(wrapper);
		if (window.bootstrap && window.bootstrap.Toast) {
			const bsToast = new window.bootstrap.Toast(wrapper, { delay: 3000 });
			bsToast.show();
			wrapper.addEventListener('hidden.bs.toast', function () {
				wrapper.remove();
			});
		} else {
			// Fallback: simple auto-remove if Bootstrap JS not loaded yet
			setTimeout(function () {
				wrapper.remove();
			}, 3200);
		}
	}

	window.notifySuccess = function (msg, title) {
		showToast(msg, 'success', title);
	};
	window.notifyError = function (msg, title) {
		showToast(msg, 'danger', title);
	};
	window.notifyInfo = function (msg, title) {
		showToast(msg, 'info', title);
	};
	window.notifyWarning = function (msg, title) {
		showToast(msg, 'warning', title);
	};

	document.addEventListener('DOMContentLoaded', function () {
		var el = document.getElementById('flash-message');
		if (!el) return;
		var type = el.getAttribute('data-type') || 'info';
		var msg = el.getAttribute('data-message') || '';
		if (!msg) return;
		switch (type) {
			case 'success':
				return window.notifySuccess(msg);
			case 'error':
			case 'danger':
				return window.notifyError(msg);
			case 'warning':
				return window.notifyWarning(msg);
			default:
				return window.notifyInfo(msg);
		}
	});
})();
