<script lang="ts">
	let settings = {
		prefix: '!',
		musicEnabled: true,
		chessEnabled: true,
		defaultVolume: 50,
		language: 'en'
	};
</script>

<div class="max-w-4xl mx-auto">
	<div class="mb-8">
		<h1 class="text-4xl font-bold bg-gradient-to-r from-cyber-blue to-cyber-purple bg-clip-text text-transparent">
			Bot Settings
		</h1>
		<p class="text-gray-400 mt-2">Configure ChordKnight for your server</p>
	</div>

	<div class="space-y-8">
		<!-- General Settings -->
		<div class="bg-gradient-to-br from-gray-900 to-gray-800 rounded-xl border border-gray-700 p-6">
			<h2 class="text-2xl font-bold mb-6">General Settings</h2>

			<div class="space-y-6">
				<div>
					<label class="block text-sm text-gray-400 mb-2">Command Prefix</label>
					<input
						type="text"
						bind:value={settings.prefix}
						placeholder="!"
						class="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:border-cyber-blue focus:outline-none transition-colors"
					/>
					<p class="text-xs text-gray-500 mt-1">The prefix used for bot commands (e.g., !play)</p>
				</div>

				<div>
					<label class="block text-sm text-gray-400 mb-2">Language</label>
					<select
						bind:value={settings.language}
						class="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:border-cyber-blue focus:outline-none transition-colors"
					>
						<option value="en">English</option>
						<option value="es">Español</option>
						<option value="fr">Français</option>
						<option value="de">Deutsch</option>
						<option value="ja">日本語</option>
					</select>
				</div>
			</div>
		</div>

		<!-- Feature Toggles -->
		<div class="bg-gradient-to-br from-gray-900 to-gray-800 rounded-xl border border-gray-700 p-6">
			<h2 class="text-2xl font-bold mb-6">Feature Toggles</h2>

			<div class="space-y-4">
				<div class="flex items-center justify-between p-4 bg-gray-800 rounded-lg">
					<div>
						<p class="font-semibold">Music Features</p>
						<p class="text-sm text-gray-400">Enable music playback and queue management</p>
					</div>
					<button
						class="w-12 h-6 rounded-full relative transition-colors {settings.musicEnabled ? 'bg-cyber-blue' : 'bg-gray-600'}"
						on:click={() => (settings.musicEnabled = !settings.musicEnabled)}
					>
						<div class="absolute top-1 w-4 h-4 bg-white rounded-full transition-all {settings.musicEnabled ? 'right-1' : 'left-1'}"></div>
					</button>
				</div>

				<div class="flex items-center justify-between p-4 bg-gray-800 rounded-lg">
					<div>
						<p class="font-semibold">Chess Features</p>
						<p class="text-sm text-gray-400">Enable chess puzzles and utilities</p>
					</div>
					<button
						class="w-12 h-6 rounded-full relative transition-colors {settings.chessEnabled ? 'bg-cyber-purple' : 'bg-gray-600'}"
						on:click={() => (settings.chessEnabled = !settings.chessEnabled)}
					>
						<div class="absolute top-1 w-4 h-4 bg-white rounded-full transition-all {settings.chessEnabled ? 'right-1' : 'left-1'}"></div>
					</button>
				</div>
			</div>
		</div>

		<!-- Music Settings -->
		{#if settings.musicEnabled}
			<div class="bg-gradient-to-br from-gray-900 to-gray-800 rounded-xl border border-gray-700 p-6">
				<h2 class="text-2xl font-bold mb-6">Music Settings</h2>

				<div class="space-y-6">
					<div>
						<label class="block text-sm text-gray-400 mb-2">Default Volume ({settings.defaultVolume}%)</label>
						<input
							type="range"
							min="0"
							max="100"
							bind:value={settings.defaultVolume}
							class="w-full"
						/>
						<div class="flex justify-between text-xs text-gray-500 mt-1">
							<span>0%</span>
							<span>50%</span>
							<span>100%</span>
						</div>
					</div>

					<div>
						<label class="block text-sm text-gray-400 mb-2">DJ Role</label>
						<input
							type="text"
							placeholder="@DJ"
							class="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:border-cyber-blue focus:outline-none transition-colors"
						/>
						<p class="text-xs text-gray-500 mt-1">Users with this role can control music playback</p>
					</div>

					<div>
						<label class="block text-sm text-gray-400 mb-2">Music Channel</label>
						<input
							type="text"
							placeholder="#music"
							class="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:border-cyber-blue focus:outline-none transition-colors"
						/>
						<p class="text-xs text-gray-500 mt-1">Restrict music commands to this channel (optional)</p>
					</div>
				</div>
			</div>
		{/if}

		<!-- Save Button -->
		<div class="flex justify-end gap-4">
			<button class="px-6 py-3 bg-gray-700 hover:bg-gray-600 rounded-lg font-semibold transition-colors">
				Reset to Default
			</button>
			<button class="px-6 py-3 bg-gradient-to-r from-cyber-blue to-cyber-purple rounded-lg font-semibold hover:shadow-lg hover:shadow-cyber-blue/50 transition-all">
				Save Changes
			</button>
		</div>
	</div>
</div>
